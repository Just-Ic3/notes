package com.disqo.notes.sessions;

import com.disqo.notes.entities.NoteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserSession implements Serializable {
    public UserSession(NoteUser noteUser) {
        id = noteUser.getId();
        email = noteUser.getEmail();
    }
    private String token;
    private String id;
    private String email;

}
