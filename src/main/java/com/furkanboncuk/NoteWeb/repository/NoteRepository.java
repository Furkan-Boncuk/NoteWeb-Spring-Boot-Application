package com.furkanboncuk.NoteWeb.repository;

import com.furkanboncuk.NoteWeb.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Note getNoteById(Long id);
    Note getNoteByTitle(String title);
    Note getNoteByCategory(String category);
    void deleteNoteByTitle(String title);
    void deleteNoteById(Long id);
}
