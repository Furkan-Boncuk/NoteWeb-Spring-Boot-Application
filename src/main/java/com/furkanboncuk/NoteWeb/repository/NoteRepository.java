package com.furkanboncuk.NoteWeb.repository;

import com.furkanboncuk.NoteWeb.modal.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findNoteByTitle(String title);
    Note getNoteByCategory(String category);
    Optional<Note> findByTitle(String title);
    Optional<Note> findNoteById(Long id);

    //filter
        //select * from note where notes_title like "%...%";
    List<Note> findByTitleContaining(String title);
        //select * from note where notes_category like "%...%";
    List<Note> findNoteByCategoryContaining(String category);
        //select * from note where notes_updated_date like "%...%";
    List<Note> findNoteByUpdatedDateContaining(LocalDateTime updatedDate);

}