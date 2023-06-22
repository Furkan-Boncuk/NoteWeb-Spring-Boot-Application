package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.entity.Note;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NoteService {

    //get
    List<Note> findAllNotes();
    //post
    Note saveNote(Note note);
    //delete
    void deleteNoteById(Long id);
    void deleteNoteByTitle(String title);
    void deleteAllNotes();

    // search
    Optional<Note> getNoteById(Long id);
    Optional<Note> getNoteByTitle(String title);
    Note getNoteByCategory(String category);

    // filter
    List<Note> filterNoteByTitle(String title);
    List<Note> filterNoteByCategory(String category);
    List<Note> filterNoteByUpdatedDate(LocalDateTime updatedDate);

    // sort
    /*
    List<Note> getNotesByCategoryAsc(String category);
    List<Note> getNotesByCategoryDesc(String category);*/



}
