package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    private NoteRepository noteRepository;
    private EntityManager entityManager;

    @Autowired
    public NoteServiceImpl(NoteRepository theNoteRepository, EntityManager entityManager) {
        this.noteRepository=theNoteRepository;
        this.entityManager=entityManager;
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
        Optional<Note> note = noteRepository.findById(id);
        entityManager.remove(note);
    }

    @Override
    public void deleteNoteByTitle(String title) {
        Optional<Note> note = Optional.ofNullable(noteRepository.getNoteByTitle(title));
        entityManager.remove(note);
    }

    @Override
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.getNoteById(id);
    }

    @Override
    public Note getNoteByTitle(String title) {
        return noteRepository.getNoteByTitle(title);
    }

    @Override
    public Note getNoteByCategory(String category) {
        return noteRepository.getNoteByCategory(category);
    }
}
