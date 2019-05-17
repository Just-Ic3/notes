package com.disqo.notes.services;

import com.disqo.notes.entities.User;
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

    public User registerNewUser(User user) {
        User alreadyExists = userRepository.findByEmail(user.getEmail());
        if(alreadyExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A user with this password already exists.");
        }
        user.setCreatedOn(new Date());
        user.setLastUpdatedOn(user.getCreatedOn());
        return userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Wrong email/password combination.");
        }
        UserSession userSession = new UserSession(user);
        userSession = hazelcastService.createSession(userSession);
        return userSession.getToken();
    }

    public void logout(String token) {
        hazelcastService.deleteSession(token);
    }

    public User editUser(User user, String userIdFromToken) {
        User foundUser = userRepository.findOneById(user.getId());
        if(foundUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The specified user could not be found.");
        }
        //TODO: get token info

        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser.setLastUpdatedOn(new Date());
        return userRepository.save(foundUser);
    }
}
