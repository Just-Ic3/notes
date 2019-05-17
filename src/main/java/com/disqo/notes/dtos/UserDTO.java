package com.disqo.notes.dtos;

import com.disqo.notes.entities.User;
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

    public UserDTO(User user) {
        email = user.getEmail();
        createdOn = user.getCreatedOn();
        lastUpdatedOn = user.getLastUpdatedOn();
    }
}
