package com.disqo.notes.services;

import com.disqo.notes.asserts.NoteAssert;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        NoteAssert.assertThat(savedNote)
                .hasCreatedDate()
                .hasLastUpdatedDate()
                .createdDateEqualsLastUpdatedDate()
                .noteFieldEquals("Note")
                .titleEquals("Title")
                .hasUser()
                .userEquals(testUser);
    }
    @Test
    void editedNoteHasDifferentLastModifiedDateAndDifferentContent() {
        //given
        Date creationDate = new Date();
        NoteUser testUser = new NoteUser("1","alex@iconos.mx","password",new Date(), new Date(),null);
        Note originalNote = new Note("1","Title","Note",creationDate,creationDate,testUser);
        Note editedNote = new Note(originalNote.getId(),"Edited Title","Edited Note",creationDate,creationDate,testUser);
        when(noteRepository.findOneById(originalNote.getId())).thenReturn(originalNote);
        when(noteRepository.save(any(Note.class))).then(returnsFirstArg());
        //when
        Note testNote = noteService.editNote(editedNote,testUser.getId());
        //then
        NoteAssert.assertThat(testNote)
                .titleEquals(editedNote.getTitle())
                .noteFieldEquals(editedNote.getNote())
                .createdDateIsDifferentFromLastUpdatedDate();
    }

    @Test
    void editingNoteWithWrongUserThrowsForbiddenException() {
        //given
        NoteUser correctUser = new NoteUser("1","alex@iconos.mx","password",new Date(), new Date(),null);
        NoteUser wrongUser = new NoteUser("2","alex@gmail.com","password1",new Date(), new Date(),null);
        Note originalNote = new Note("1","Title","Note",new Date(),new Date(),correctUser);
        when(noteRepository.findOneById(originalNote.getId())).thenReturn(originalNote);
        //when then
        assertThrows(ResponseStatusException.class, () -> {
            Note testNote = noteService.editNote(originalNote,wrongUser.getId());
        },"This noteUser doesn't have access to the specified note.");
    }
}
