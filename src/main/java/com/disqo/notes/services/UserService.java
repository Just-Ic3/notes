package com.disqo.notes.services;

import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.requests.LoginRequest;
import com.disqo.notes.sessions.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HazelcastService hazelcastService;

    public NoteUser registerNewUser(NoteUser noteUser) {
        NoteUser alreadyExists = userRepository.findByEmail(noteUser.getEmail());
        if(alreadyExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A noteUser with this password already exists.");
        }
        noteUser.setCreatedOn(new Date());
        noteUser.setLastUpdatedOn(noteUser.getCreatedOn());
        return userRepository.save(noteUser);
    }

    public String login(LoginRequest loginRequest) {
        NoteUser noteUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
        if(noteUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Wrong email/password combination.");
        }
        UserSession userSession = new UserSession(noteUser);
        userSession = hazelcastService.createSession(userSession);
        return userSession.getToken();
    }

    public void logout(String token) {
        hazelcastService.deleteSession(token);
    }

    public NoteUser editUser(NoteUser noteUser, String userIdFromToken) {
        NoteUser foundNoteUser = userRepository.findOneById(noteUser.getId());
        if(foundNoteUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The specified noteUser could not be found.");
        }
        //TODO: get token info

        foundNoteUser.setEmail(noteUser.getEmail());
        foundNoteUser.setPassword(noteUser.getPassword());
        foundNoteUser.setLastUpdatedOn(new Date());
        return userRepository.save(foundNoteUser);
    }
}
