package com.furkanboncuk.NoteWeb.controller;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.exception.NoteTitleException;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import com.furkanboncuk.NoteWeb.service.NoteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteRestController {
    private NoteServiceImpl noteService;

    @Autowired
    public NoteRestController(NoteServiceImpl noteService) {
        this.noteService = noteService;
    }

    // localhost:4000/notes/save
    @PostMapping("/save")
    public ResponseEntity<Object> createNewNote(@Valid @RequestBody Note note) throws NoteTitleException {
        List<Note> existingNotes = noteService.findAllNotes();

        for(Note obj : existingNotes) {
            if(obj.getTitle().equals(note.getTitle())) {
                throw new NoteTitleException();
            }
        }
        noteService.saveNote(note);
        return new ResponseEntity<>(note,HttpStatus.OK);
    }

    // localhost:4000/notes/all
    @GetMapping("/all")
    public ResponseEntity<List<Note>> displayAllNotes() {
        List<Note> allNotes = noteService.findAllNotes().stream().collect(Collectors.toList());
        return new ResponseEntity<>(allNotes, HttpStatus.OK);
    }

    // localhost:4000/notes/update/{keyword}
    @PutMapping({"/update/{keyword}"})
    public ResponseEntity<Note> updateNoteById(@Valid @PathVariable("keyword") String keyword,
                             @RequestBody Note note) {
        Note newNote = new Note();
        Note oldNote;
        if(keyword.matches("\\d+")) {
            Long id = Long.parseLong(keyword);
            oldNote = noteService.getNoteById(id);
        } else {
            oldNote = noteService.getNoteByTitle(keyword);
        }

        if(oldNote!=null) {
            newNote.setId(oldNote.getId());
            newNote.setCreatedDate(oldNote.getCreatedDate());
            newNote.setTitle(note.getTitle());
            newNote.setContent(note.getContent());
            newNote.setCategory(note.getCategory());
            newNote.setUpdatedDate(Date.from(Instant.now()));

            noteService.saveNote(newNote);
            return new ResponseEntity<>(newNote,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //localhost:4000/notes/delete/{keyword}
    @DeleteMapping("/delete/{keyword}")
    public String deleteNote(@PathVariable("keyword") String keyword) {
        if(keyword.matches("\\d+")) {
            Long id = Long.parseLong(keyword);
            noteService.deleteNoteById(id);
        } else {
            noteService.deleteNoteByTitle(keyword);
        }
        return "Note was deleted, successfully";
    }

}
