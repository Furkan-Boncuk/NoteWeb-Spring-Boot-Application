package com.furkanboncuk.NoteWeb.controller;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.exception.NoteDoesNotExistException;
import com.furkanboncuk.NoteWeb.exception.NoteTitleException;
import com.furkanboncuk.NoteWeb.service.NoteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.ListDataEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
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

        for (Note obj : existingNotes) {
            if (obj.getTitle().equals(note.getTitle())) {
                throw new NoteTitleException();
            }
        }
        noteService.saveNote(note);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    // localhost:4000/notes/all
    @GetMapping("/all")
    public ResponseEntity<List<Note>> displayAllNotes() {
        List<Note> allNotes = noteService.findAllNotes().stream().collect(Collectors.toList());
        return new ResponseEntity<>(allNotes, HttpStatus.OK);
    }

    // localhost:4000/notes/update/{id}
    @PutMapping("/update/byId/{id}")
    public ResponseEntity<?> updateNoteById(@Valid @PathVariable("id") Long id,
                                               @RequestBody Note note) throws NoteTitleException{
        Note newNote = new Note();
        Optional<Note> oldNote = noteService.getNoteById(id);

        newNote.setId(oldNote.get().getId());
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setCategory(note.getCategory());
        newNote.setUpdatedDate(LocalDateTime.now());

        if (newNote.getTitle().equals(oldNote.get().getTitle()) && newNote.getContent().equals(oldNote.get().getContent())) {
            throw new NoteTitleException();
        } else {
            noteService.saveNote(newNote);
            return new ResponseEntity<>(newNote, HttpStatus.OK);
        }
    }

    // localhost:4000/notes/update/{title}
    @PutMapping("/update/byTitle/{title}")
    public ResponseEntity<Object> updateNoteByTitle(@Valid @PathVariable("title") String title,
                                                  @RequestBody Note note) throws NoteTitleException{
        Note newNote = new Note();
        Optional<Note> oldNote = noteService.getNoteByTitle(title);

        newNote.setId(oldNote.get().getId());
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setCategory(note.getCategory());
        newNote.setUpdatedDate(LocalDateTime.now());

        if (newNote.getTitle().equals(oldNote.get().getTitle()) && newNote.getContent().equals(oldNote.get().getContent())) {
            throw new NoteTitleException();
        } else {
            noteService.saveNote(newNote);
            return new ResponseEntity<>(newNote, HttpStatus.OK);
        }
    }

    //localhost:4000/notes/delete/byId/{id}
    @DeleteMapping("/delete/byId/{id}")
    public String deleteNoteByIdMethod(@PathVariable("id") Long id) {
        Optional<Note> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            noteService.deleteNoteById(id);
            return "Note was deleted, successfully : " + id;
        } else {
            throw new NoteDoesNotExistException();
        }
    }

    //localhost:4000/notes/delete/byTitle/{title}
    @DeleteMapping("/delete/byTitle/{title}")
    public String deleteNoteByTitleMethod(@PathVariable String title) {
        Optional<Note> note = noteService.getNoteByTitle(title);
        if (note.isPresent()) {
            noteService.deleteNoteByTitle(title);
            return "Note was deleted successfully: " + title;
        } else {
            throw new NoteDoesNotExistException();
        }
    }

    //localhost:4000/searchNote/byId/{id}
    @GetMapping("/searchNote/byId/{id}")
    public ResponseEntity<List<Note>> displayNoteById(@PathVariable("id") Long id) {
        List<Note> notes = noteService.findAllNotes().stream().filter(item -> item.getId()==id).collect(Collectors.toList());
        if (notes.isEmpty()) {
            throw new NoteDoesNotExistException();
        } else {
            return new ResponseEntity<>(notes,HttpStatus.OK);
        }
    }

    //localhost:4000/searchNote/byTitle/{title}
    @GetMapping("/searchNote/byTitle/{title}")
    public ResponseEntity<List<Note>> displayNoteByTitle(@PathVariable("title") String title) {
        List<Note> notes = noteService.findAllNotes().stream().filter(item -> item.getTitle().equals(title)).collect(Collectors.toList());
        if (notes.isEmpty()) {
            throw new NoteDoesNotExistException();
        } else {
            return new ResponseEntity<>(notes,HttpStatus.OK);
        }
    }

    //localhost:4000/searchNote/byCategory/{category}
    @GetMapping("/searchNote/byCategory/{category}")
    public ResponseEntity<List<Note>> displayNotesByCategory(@PathVariable("category") String category) {
        List<Note> notes = noteService.findAllNotes().stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());
        if (notes.isEmpty()) {
            throw new NoteDoesNotExistException();
        } else {
            return new ResponseEntity<>(notes,HttpStatus.OK);
        }
    }

    //localhost:4000/filterNote/byTitle/{title}
    @GetMapping("/filterNote/byTitle")
    public ResponseEntity<?> filterNotesByTitleParam(@RequestParam(required = false) String title) {
        if(title==null || title.isEmpty()) {
            return ResponseEntity.badRequest().body("Title parameter is required.");
        }

        try {
            List<Note> filteredNotes = noteService.filterNoteByTitle(title);
            if (filteredNotes.isEmpty()) {
                return ResponseEntity.notFound().build();
                //throw new NoteDoesNotExistException();
            }
            return ResponseEntity.ok(filteredNotes);

        } catch (NoteDoesNotExistException ex) {
            return ResponseEntity.notFound().build();
            //throw new NoteDoesNotExistException();
        }
    }

    //localhost:4000/filterNote/byCategory/{category}
    @GetMapping("/filterNote/byCategory")
    public ResponseEntity<?> filterNotesByCategoryParam(@RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return ResponseEntity.badRequest().body("Category parameter is required.");
        }

        try {
            List<Note> filteredNotes = noteService.filterNoteByCategory(category);
            if (filteredNotes.isEmpty()) {
                return ResponseEntity.notFound().build();
                //throw new NoteDoesNotExistException();
            }
            return ResponseEntity.ok(filteredNotes);

        } catch (NoteDoesNotExistException ex) {
            return ResponseEntity.notFound().build();
            //throw new NoteDoesNotExistException();
        }
    }

    //localhost:4000/notes
    @GetMapping
    public ResponseEntity<List<Note>> getNotes(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return new ResponseEntity<>(noteService.getNotes(pageNumber,pageSize),HttpStatus.OK);

    }

    //localhost:4000/notes/sortByTitleASC
    @GetMapping("/sortByTitleASC")
    public ResponseEntity<?> getNotesByTitleSortingASCMethod(@RequestParam String title,
                                                                      @RequestParam int pageNumber,
                                                                      @RequestParam int pageSize) {

        List<Note> searching = noteService.getNotesByTitleSortingASC(title,pageNumber,pageSize);
        if(searching.isEmpty()) {
            throw new NoteDoesNotExistException();
        } else {
            return new ResponseEntity<>(searching,HttpStatus.OK);
        }

    }

    //localhost:4000/notes/sortByTitleDESC
    @GetMapping("/sortByTitleDESC")
    public ResponseEntity<?> getNotesByTitleSortingDESCMethod(@RequestParam String title,
                                                                       @RequestParam int pageNumber,
                                                                       @RequestParam int pageSize) {

        List<Note> searching = noteService.getNotesByTitleSortingDESC(title,pageNumber,pageSize);
        if(searching.isEmpty()) {
            throw new NoteDoesNotExistException();
        } else {
            return new ResponseEntity<>(searching,HttpStatus.OK);
        }
    }



    /*
    //localhost:4000/filterNote/byUpdatedDate/{updatedDate}
    @GetMapping("/filterNote/byUpdatedDate")
    public ResponseEntity<?> filterNotesByUpdatedDateParam(@RequestParam(required = false) LocalDateTime updatedDate) {
        String formattedDate = updatedDate.toString();
        if (formattedDate.equals(null)) {
            return ResponseEntity.badRequest().body("Updated Date parameter is required.");
        }

        try {
            List<Note> filteredNotes = noteService.filterNoteByUpdatedDate(formattedDate);
            if (filteredNotes.isEmpty()) {
                return ResponseEntity.notFound().build();
                //throw new NoteDoesNotExistException();
            }
            return ResponseEntity.ok(filteredNotes);

        } catch (NoteDoesNotExistException ex) {
            return ResponseEntity.notFound().build();
            //throw new NoteDoesNotExistException();
        }
    }
    */

}
