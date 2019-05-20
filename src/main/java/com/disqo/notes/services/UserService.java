package com.disqo.notes.services;

import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.requests.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public NoteUser registerNewUser(SignupRequest signupRequest) {
        NoteUser alreadyExists = userRepository.findByEmail(signupRequest.getEmail());
        if(alreadyExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A noteUser with this email already exists.");
        }
        NoteUser noteUser = new NoteUser(signupRequest);
        return userRepository.save(noteUser);
    }

//    public String login(LoginRequest loginRequest) {
//        NoteUser noteUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
//        if(noteUser == null) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Wrong email/password combination.");
//        }
//        UserSession userSession = new UserSession(noteUser);
//        userSession = hazelcastService.createSession(userSession);
//        return userSession.getToken();
//    }
//
//    public void logout(String token) {
//        hazelcastService.deleteSession(token);
//    }

    public NoteUser editUser(String email, String userId) {
        NoteUser repeatedEmail = userRepository.findByEmail(email);
        if(repeatedEmail != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The specified email is already registered.");
        }
        NoteUser foundNoteUser = userRepository.findOneById(userId);
        if(foundNoteUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The specified noteUser could not be found.");
        }
        foundNoteUser.setEmail(email);
        foundNoteUser.setLastUpdatedOn(new Date());
        return userRepository.save(foundNoteUser);
    }
}
