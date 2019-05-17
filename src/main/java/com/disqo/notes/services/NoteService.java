package com.disqo.notes.services;

import com.disqo.notes.entities.Note;
import com.disqo.notes.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public ArrayList<Note> findAllByUser(String userId) {
        return noteRepository.findAllByUser_Id(userId);
    }

    public Note getNoteById(String id) {
        return noteRepository.getOne(id);
    }

    public Note createNewNote(Note note) {
        if(note.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't create an already existing note.");
        }
        note.setCreatedOn(new Date());
        note.setLastUpdatedOn(note.getCreatedOn());
        return noteRepository.save(note);
    }

    public Note editNote(Note note) {
        Note existingNote = noteRepository.findOneById(note.getId());
        if(existingNote == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The specified note could not be found.");
        }
        existingNote.setTitle(note.getTitle());
        existingNote.setNote(note.getNote());
        existingNote.setLastUpdatedOn(new Date());
        return noteRepository.save(note);
    }


}
