package com.furkanboncuk.NoteWeb.repository;

import com.furkanboncuk.NoteWeb.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findNoteByCategory(String category);



}
