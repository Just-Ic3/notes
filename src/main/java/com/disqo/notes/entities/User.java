package com.disqo.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
public class User {
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    @OrderBy("lastUpdatedOn DESC ")
    private ArrayList<Note> notes;
}
