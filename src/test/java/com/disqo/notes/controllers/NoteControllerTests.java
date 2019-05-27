package com.disqo.notes.controllers;

import com.disqo.notes.entities.Note;
import com.disqo.notes.requests.NoteCreationRequest;
import com.disqo.notes.services.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @Test
    @Sql(scripts = "/test-user.sql")
    @WithUserDetails("alex@iconos.mx")
    void whenValidInput_thenReturns200() throws Exception {
        NoteCreationRequest noteCreationRequest = new NoteCreationRequest("Title","Note");
        Note createdNote = new Note(noteCreationRequest);
        when(noteService.createNewNote(noteCreationRequest,"1")).thenReturn(createdNote);
        mockMvc.perform(post("/notes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(noteCreationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/test-user.sql")
    @WithUserDetails("alex@iconos.mx")
    void whenNullInput_thenReturns400() throws Exception {
        NoteCreationRequest badNoteCreationRequest = new NoteCreationRequest(null,"Note");
        mockMvc.perform(post("/notes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(badNoteCreationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/test-user.sql")
    @WithUserDetails("alex@iconos.mx")
    void whenValidInputOnPut_thenReturn200() throws Exception {
        Note testNote = new Note("1","Title","Note",new Date(),new Date(),null);
        when(noteService.editNote(eq(testNote),any(String.class))).thenReturn(testNote);
        mockMvc.perform(put("/notes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testNote)))
                .andExpect(status().isOk());
    }
}
