package com.furkanboncuk.NoteWeb.controller;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.exception.NoteDoesNotExistException;
import com.furkanboncuk.NoteWeb.exception.NoteInformationException;
import com.furkanboncuk.NoteWeb.exception.NoteTitleException;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import com.furkanboncuk.NoteWeb.service.NoteServiceImpl;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    // localhost:4000/notes/update/{id}
    @PutMapping("/update/byId/{id}")
    public ResponseEntity<Note> updateNoteById(@Valid @PathVariable("id") Long id,
                                                @RequestBody Note note) {
        Note newNote = new Note();
        Optional<Note> oldNote = noteService.getNoteById(id);

        newNote.setId(oldNote.get().getId());
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setCategory(note.getCategory());
        newNote.setUpdatedDate(Date.from(Instant.now()));

        if(newNote.getTitle().equals(oldNote.get().getTitle()) && newNote.getContent().equals(oldNote.get().getContent())) {
            throw new NoteTitleException();
        } else {
            noteService.saveNote(newNote);
            return new ResponseEntity<>(newNote,HttpStatus.OK);
        }
    }

    // localhost:4000/notes/update/{title}
    @PutMapping("/update/byTitle/{title}")
    public ResponseEntity<Note> updateNoteByTitle(@Valid @PathVariable("title") String title,
                                               @RequestBody Note note) {
        Note newNote = new Note();
        Optional<Note> oldNote = noteService.getNoteByTitle(title);

        newNote.setId(oldNote.get().getId());
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setCategory(note.getCategory());
        newNote.setUpdatedDate(Date.from(Instant.now()));

        if(newNote.getTitle().equals(oldNote.get().getTitle()) && newNote.getContent().equals(oldNote.get().getContent())) {
            throw new NoteTitleException();
        } else {
            noteService.saveNote(newNote);
            return new ResponseEntity<>(newNote,HttpStatus.OK);
        }
    }
    /*
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
    }*/

    //localhost:4000/notes/delete/byId/{id}
    @DeleteMapping("/delete/byId/{id}")
    public String deleteNoteByIdMethod(@PathVariable("id") Long id) {
        noteService.deleteNoteById(id);
        return "Note was deleted, successfully : "+id;
    }

    //localhost:4000/notes/delete/byTitle/{title}
    @DeleteMapping("/delete/byTitle/{title}")
    public String deleteNoteByTitleMethod(@PathVariable String title) {
        Optional<Note> note = noteService.getNoteByTitle(title);
        if(note.isPresent()) {
            noteService.deleteNoteByTitle(title);
            return "Note was deleted successfully: " + title;
        } else {
            throw new NoteDoesNotExistException();
        }
    }

    /*
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
*/
}
