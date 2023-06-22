package com.furkanboncuk.NoteWeb.repository;

import com.furkanboncuk.NoteWeb.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Note getNoteById(Long id);
    Optional<Note> findNoteByTitle(String title);
    Note getNoteByCategory(String category);
    Optional<Note> findByTitle(String title);
    Optional<Note> findNoteById(Long id);
}
