package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    private NoteRepository noteRepository;
    //private EntityManager entityManager;

    @Autowired
    public NoteServiceImpl(NoteRepository theNoteRepository/*, EntityManager entityManager*/) {
        this.noteRepository=theNoteRepository;
        //this.entityManager=entityManager;
    }

    @Override
    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public void deleteNoteByTitle(String title) {
        Optional<Note> note = noteRepository.findByTitle(title);
        note.ifPresent(noteRepository::delete);
    }

    @Override
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    @Override
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findNoteById(id);
    }

    @Override
    public Optional<Note> getNoteByTitle(String title) {
        return noteRepository.findNoteByTitle(title);
    }

    @Override
    public Note getNoteByCategory(String category) {
        return noteRepository.getNoteByCategory(category);
    }
}
