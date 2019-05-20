package com.disqo.notes.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NoteCreationRequest {
    @Size(min = 1, max = 50, message = "Title must not be empty nor exceed 50 characters.")
    private String title;
    @Size(max = 1000, message = "Note must not exceed 1000 characters.")
    private String note;
}
