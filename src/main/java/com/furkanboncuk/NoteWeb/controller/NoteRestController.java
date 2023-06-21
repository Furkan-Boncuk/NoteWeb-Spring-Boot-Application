package com.furkanboncuk.NoteWeb.controller;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.exceptionHandler.NoteTitleException;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteRestController {
    private NoteRepository noteRepository;

    @Autowired
    public NoteRestController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // localhost:4000/notes/save
    @PostMapping("/save")
    public ResponseEntity<Object> createNewNote(@RequestBody Note note) throws NoteTitleException {
        List<Note> existingNotes = noteRepository.findAll();

        for(Note obj : existingNotes) {
            if(obj.getTitle().equals(note.getTitle())) {
                throw new NoteTitleException();
            }
        }
        noteRepository.save(note);
        return new ResponseEntity<>(note,HttpStatus.OK);
    }

    // localhost:4000/notes/all
    @GetMapping("/all")
    public ResponseEntity<List<Note>> displayAllNotes() {
        List<Note> allNotes = noteRepository.findAll().stream().collect(Collectors.toList());
        return new ResponseEntity<>(allNotes, HttpStatus.OK);
    }
    // localhost:4000/notes/update/{keyword}
    /*@PutMapping("/update/{keyword}")
    public String updateNote(@PathVariable("id") Long id,
                             @PathVariable("title") String title) {


    }*/

}
