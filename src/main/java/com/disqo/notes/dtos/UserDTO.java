package com.disqo.notes.dtos;

import com.disqo.notes.entities.NoteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String email;
    private Date createdOn;
    private Date lastUpdatedOn;

    public UserDTO(NoteUser noteUser) {
        email = noteUser.getEmail();
        createdOn = noteUser.getCreatedOn();
        lastUpdatedOn = noteUser.getLastUpdatedOn();
    }
}
