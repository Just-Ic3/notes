package com.disqo.notes.repositories;

import com.disqo.notes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,String> {
    List<Note> findAllByUser_Id(String id);
}
