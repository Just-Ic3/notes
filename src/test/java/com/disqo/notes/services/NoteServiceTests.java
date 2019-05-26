package com.disqo.notes.services;

import com.disqo.notes.entities.Note;
import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.repositories.NoteRepository;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.requests.NoteCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTests {

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private UserRepository userRepository;

    private NoteService noteService;

    @BeforeEach
    void initializeService() {
        noteService = new NoteService(noteRepository,userRepository);
    }

    @Test
    void savedNoteHasCreatedDateEqualsLastModifiedDateAndHasNoteUser() {
        //given
        NoteCreationRequest noteCreationRequest = new NoteCreationRequest("Title","Note");
        NoteUser testUser = new NoteUser("1","alex@iconos.mx","password",new Date(),new Date(),null);
        when(userRepository.findOneById("1")).thenReturn(testUser);
        when(noteRepository.save(any(Note.class))).then(returnsFirstArg());
        //when
        Note savedNote = noteService.createNewNote(noteCreationRequest,"1");
        //then
        assertThat(savedNote.getCreatedOn()).isNotNull();
        assertThat(savedNote.getLastUpdatedOn()).isNotNull().isEqualTo(savedNote.getCreatedOn());
        assertThat(savedNote.getTitle().equals("Title")).isTrue();
        assertThat(savedNote.getNote().equals("Note")).isTrue();
        assertThat(savedNote.getNoteUser()).isEqualTo(testUser);
    }


}
