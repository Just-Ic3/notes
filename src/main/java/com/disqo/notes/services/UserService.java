package com.disqo.notes.services;

import com.disqo.notes.entities.User;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.requests.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
        //TODO
        return "";
    }

    public User editUser(User user, String token) {
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
