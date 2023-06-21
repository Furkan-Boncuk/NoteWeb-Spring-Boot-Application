package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.entity.Note;

import java.util.List;

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
    Note getNoteById(Long id);
    Note getNoteByTitle(String title);
    Note getNoteByCategory(String category);

    // sort
    /*
    List<Note> getNotesByCategoryAsc(String category);
    List<Note> getNotesByCategoryDesc(String category);*/



}
