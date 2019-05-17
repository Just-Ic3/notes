package com.disqo.notes.sessions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserSession implements Serializable {
    private String token;
    private String id;
    private String email;

}
