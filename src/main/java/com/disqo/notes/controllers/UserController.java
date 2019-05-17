package com.disqo.notes.controllers;

import com.disqo.notes.dtos.UserDTO;
import com.disqo.notes.entities.User;
import com.disqo.notes.requests.LoginRequest;
import com.disqo.notes.services.HazelcastService;
import com.disqo.notes.services.UserService;
import com.disqo.notes.sessions.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HazelcastService hazelcastService;

    @PostMapping(path = "/public/register", consumes = "application/json", produces = "application/json")
    public UserDTO registerNewUser(@RequestBody @Valid User user) {
        return new UserDTO(userService.registerNewUser(user));
    }

    @PostMapping(path = "/public/login", consumes = "application/json", produces = "plain/text")
    public String login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("token") String token) {
        userService.logout(token);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public UserDTO editUser(@RequestBody User user, @RequestHeader("token") String token) {
        UserSession session = getSession(token);
        return new UserDTO(userService.editUser(user, session.getId()));
    }

    private UserSession getSession(String token) {
        UserSession session = hazelcastService.getSession(token);
        if(session == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid user token");
        }
        return session;
    }
}

