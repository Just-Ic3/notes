package com.disqo.notes.controllers;

import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.requests.SignupRequest;
import com.disqo.notes.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        SignupRequest signupRequest = new SignupRequest("alex@iconos.mx","password");
        NoteUser registeredUser = new NoteUser(signupRequest);
        when(userService.registerNewUser(any(SignupRequest.class))).thenReturn(registeredUser);
        mockMvc.perform(post("/users/public/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void whenNullValue_thenReturn400() throws Exception {
        SignupRequest badSignupRequest = new SignupRequest(null,"password");
        mockMvc.perform(post("/users/public/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(badSignupRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = "alex@iconos.mx")
    @Sql("/test-user.sql")
    void whenValidEmailOnPatch_ThenReturn200() throws Exception {
        String newEmail = "alex@gmail.com";
        NoteUser editedUser = new NoteUser("1",newEmail,"password",new Date(),new Date(),null);
        when(userService.editUser(any(String.class),any(String.class))).thenReturn(editedUser);
        mockMvc.perform(patch("/users/email")
                .contentType("text/plain")
                .content(newEmail))
                .andExpect(status().isOk());
    }
}