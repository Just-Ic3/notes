package com.disqo.notes.controllers;

import com.disqo.notes.dtos.UserDTO;
import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.requests.LoginRequest;
import com.disqo.notes.requests.SignupRequest;
import com.disqo.notes.services.HazelcastService;
import com.disqo.notes.services.UserService;
import com.disqo.notes.sessions.NoteUserPrincipal;
import com.disqo.notes.sessions.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/public/register", consumes = "application/json", produces = "application/json")
    public UserDTO registerNewUser(@RequestBody @Valid SignupRequest request) {
        request.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        return new UserDTO(userService.registerNewUser(request));
    }

//    @PostMapping(path = "/public/login", consumes = "application/json", produces = "plain/text")
//    public String login(@RequestBody @Valid LoginRequest loginRequest) {
//        return userService.login(loginRequest);
//    }
//
//    @PostMapping("/logout")
//    public void logout(@RequestHeader("token") String token) {
//        userService.logout(token);
//    }

    @PatchMapping(path = "/email", consumes = "application/json", produces = "application/json")
    public UserDTO editUserEmail(@RequestBody @Email String email, @AuthenticationPrincipal NoteUserPrincipal userPrincipal) {
        return new UserDTO(userService.editUser(email, userPrincipal.getId()));
    }
}

