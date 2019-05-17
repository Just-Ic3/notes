package com.disqo.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Note {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @Size(min = 1, max = 50, message = "Title must not be empty nor exceed 50 characters.")
    private String title;
    @Size(max = 1000, message = "Note must not exceed 1000 characters.")
    private String note;

    private Date createdOn;
    private Date lastUpdatedOn;

    @ManyToOne(targetEntity = NoteUser.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("notes")
    private NoteUser noteUser;
}
