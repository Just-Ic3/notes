package com.disqo.notes.services;

import com.disqo.notes.entities.Note;
import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.repositories.NoteRepository;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.requests.NoteCreationRequest;
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
    private final UserRepository userRepository;

    public ArrayList<Note> findAllByUser(String userId) {
        return noteRepository.findAllByNoteUser_Id(userId);
    }

    public Note getNoteById(String id, String userId) {
        Note note = noteRepository.getOne(id);
        if(note == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Note not found.");
        }
        if(!note.getNoteUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This noteUser doesn't have access to the specified note.");
        }
        note.setNoteUser(null);
        return note;
    }

    public Note createNewNote(NoteCreationRequest request, String userId) {
        NoteUser noteUser = userRepository.findOneById(userId);
        if(noteUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The noteUser does not exist.");
        }
        Note note = new Note(request);
        note.setNoteUser(noteUser);
        return noteRepository.save(note);
    }

    public Note editNote(Note note, String userId) {
        Note existingNote = noteRepository.findOneById(note.getId());
        if(existingNote == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The specified note could not be found.");
        }
        if(!existingNote.getNoteUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This noteUser doesn't have access to the specified note.");
        }
        existingNote.setTitle(note.getTitle());
        existingNote.setNote(note.getNote());
        existingNote.setLastUpdatedOn(new Date());
        return noteRepository.save(existingNote);
    }
}
