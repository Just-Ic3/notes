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
    private final HazelcastService hazelcastService;

    @GetMapping()
    ArrayList<Note> getNotes(@RequestHeader(name = "token") String token) {
        //TODO: get noteUser info from token
        UserSession session = getSession(token);
        return noteService.findAllByUser(session.getId());
    }

    @GetMapping("/{id}")
    Note getNoteById(@PathVariable("id") String id, @RequestHeader(name = "token") String token) {
        UserSession session = getSession(token);
        return noteService.getNoteById(id,session.getId());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    Note createNewNote(@RequestBody @Valid NoteCreationRequest request, @AuthenticationPrincipal NoteUserPrincipal principal) {
        return noteService.createNewNote(request,principal.getId());
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    Note editNote(@RequestBody @Valid Note note, @RequestHeader(name = "token") String token) {
        UserSession session = getSession(token);
        return noteService.editNote(note,session.getId());
    }

    private UserSession getSession(String token) {
        UserSession session = hazelcastService.getSession(token);
        if(session == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid noteUser token");
        }
        return session;
    }
}
