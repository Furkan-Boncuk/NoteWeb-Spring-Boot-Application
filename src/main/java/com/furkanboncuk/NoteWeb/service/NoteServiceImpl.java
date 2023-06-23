package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.repository.NotePaginationRepository;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    private NoteRepository noteRepository;
    private NotePaginationRepository notePaginationRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NotePaginationRepository notePaginationRepository) {
        this.noteRepository=noteRepository;
        this.notePaginationRepository=notePaginationRepository;
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
        return noteRepository.findById(id);
    }

    @Override
    public Optional<Note> getNoteByTitle(String title) {
        return noteRepository.findNoteByTitle(title);
    }

    @Override
    public Note getNoteByCategory(String category) {
        return noteRepository.getNoteByCategory(category);
    }

    @Override
    public List<Note> filterNoteByTitle(String title) {
        return noteRepository.findByTitleContaining(title);
    }

    @Override
    public List<Note> filterNoteByCategory(String category) {
        return noteRepository.findNoteByCategoryContaining(category);
    }

    @Override
    public List<Note> filterNoteByUpdatedDate(LocalDateTime updatedDate) {
        return noteRepository.findNoteByUpdatedDateContaining(updatedDate);
    }

    @Override
    public List<Note> getNotes(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC, "id");
        return notePaginationRepository.findAll(pages).getContent();
    }

    @Override
    public List<Note> getNotesByTitleSortingASC(String title, int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC, "id");
        return notePaginationRepository.findNoteByTitleContaining(title, pages);
    }

    @Override
    public List<Note> getNotesByTitleSortingDESC(String title, int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber,pageSize, Sort.Direction.DESC, "id");
        return notePaginationRepository.findNoteByTitleContaining(title, pages);
    }



}