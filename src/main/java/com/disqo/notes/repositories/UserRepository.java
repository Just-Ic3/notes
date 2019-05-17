package com.disqo.notes.repositories;

import com.disqo.notes.entities.NoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<NoteUser,String> {
    NoteUser findByEmailAndPassword(String email, String password);
    NoteUser findByEmail(String email);
    NoteUser findOneById(String id);
}
