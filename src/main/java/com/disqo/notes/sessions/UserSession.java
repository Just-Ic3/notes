package com.disqo.notes.sessions;

import com.disqo.notes.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserSession implements Serializable {
    public UserSession(User user) {
        id = user.getId();
        email = user.getEmail();
    }
    private String token;
    private String id;
    private String email;

}
