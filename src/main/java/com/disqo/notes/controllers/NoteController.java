package com.disqo.notes.controllers;

import com.disqo.notes.entities.Note;
import com.disqo.notes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/notes")
@CrossOrigin
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping()
    ArrayList<Note> getNotes() {
        return noteService.findAllByUser(userId);
    }

    @GetMapping("/{id}")
    Note getNoteById(@PathVariable("id") String id) {
        return noteService.getNoteById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    Note createNewNote(@RequestBody @Valid Note note) {
        return noteService.createNewNote(note);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    Note editNote(@RequestBody @Valid Note note) {
        return noteService.editNote(note);
    }
}
