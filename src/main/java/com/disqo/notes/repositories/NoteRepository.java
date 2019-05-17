package com.disqo.notes.repositories;

import com.disqo.notes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NoteRepository extends JpaRepository<Note,String> {
    ArrayList<Note> findAllByUser_Id(String userId);
    Note findOneById(String id);
}
