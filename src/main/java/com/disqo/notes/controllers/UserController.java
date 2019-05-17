package com.disqo.notes.controllers;

import com.disqo.notes.dtos.UserDTO;
import com.disqo.notes.entities.User;
import com.disqo.notes.requests.LoginRequest;
import com.disqo.notes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/public/register", consumes = "application/json", produces = "application/json")
    UserDTO registerNewUser(@RequestBody @Valid User user) {
        return new UserDTO(userService.registerNewUser(user));
    }

    @PostMapping(path = "/public/login", consumes = "application/json", produces = "plain/text")
    String login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    UserDTO editUser(@RequestBody User user, @RequestHeader("token") String token) {
        return new UserDTO(userService.editUser(user, token));
    }
}

