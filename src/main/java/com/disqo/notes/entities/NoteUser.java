package com.disqo.notes.entities;

import com.disqo.notes.requests.SignupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class NoteUser {

    public NoteUser(SignupRequest signupRequest) {
        email = signupRequest.getEmail();
        password = signupRequest.getPassword();
        createdOn = new Date();
        lastUpdatedOn = createdOn;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @Email(message = "Please enter a valid email.")
    private String email;

    @Size(min = 8, message = "Password should be a minimum of 8 characters.")
    private String password;

    private Date createdOn;
    private Date lastUpdatedOn;

    @OneToMany(mappedBy = "noteUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("noteUser")
    @OrderBy("lastUpdatedOn DESC ")
    @JsonIgnore
    private List<Note> notes;
}
