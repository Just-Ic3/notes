package com.disqo.notes.controllers;

import com.disqo.notes.entities.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/notes")
@CrossOrigin
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping()
    ArrayList<Note> getNotes() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    Note getNoteById(@PathVariable("id") String id) {
        return noteService.getNoteById(id);
    }

    @PostMapping()
    Note createNewNote(Note note) {
        return noteService.createNewNote(note);
    }

    @PutMapping()
    Note editNote(Note note) {
        return noteService.editNote(note);
    }
}
