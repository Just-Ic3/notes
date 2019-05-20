package com.disqo.notes.controllers;

import com.disqo.notes.entities.Note;
import com.disqo.notes.requests.NoteCreationRequest;
import com.disqo.notes.services.HazelcastService;
import com.disqo.notes.services.NoteService;
import com.disqo.notes.sessions.NoteUserPrincipal;
import com.disqo.notes.sessions.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/notes")
@CrossOrigin
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping()
    ArrayList<Note> getNotes(@AuthenticationPrincipal NoteUserPrincipal userPrincipal) {
        return noteService.findAllByUser(userPrincipal.getId());
    }

    @GetMapping("/{id}")
    Note getNoteById(@PathVariable("id") String id, @AuthenticationPrincipal NoteUserPrincipal userPrincipal) {
        return noteService.getNoteById(id,userPrincipal.getId());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    Note createNewNote(@RequestBody @Valid NoteCreationRequest request, @AuthenticationPrincipal NoteUserPrincipal principal) {
        return noteService.createNewNote(request,principal.getId());
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    Note editNote(@RequestBody @Valid Note note, @AuthenticationPrincipal NoteUserPrincipal noteUserPrincipal) {
        return noteService.editNote(note,noteUserPrincipal.getId());
    }
}
