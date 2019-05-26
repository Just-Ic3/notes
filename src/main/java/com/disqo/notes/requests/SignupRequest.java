package com.disqo.notes.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @Email(message = "Please enter a valid email.")
    private String email;

    @Size(min = 8, message = "Password should be a minimum of 8 characters.")
    private String password;
}


