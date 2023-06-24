package com.furkanboncuk.NoteWeb.controller;

import com.furkanboncuk.NoteWeb.modal.Note;
import com.furkanboncuk.NoteWeb.service.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/app")
public class NoteController {

    private NoteServiceImpl noteService;

    @Autowired
    public NoteController(NoteServiceImpl noteService) {
        this.noteService=noteService;
    }

    //localhost:4000/app/homepage
    @GetMapping("/homepage")
    public String displayHomepage() {
        return "home";
    }

    @GetMapping("/note-display")
    public String displayNoteDisplay() {
        return "note-display";
    }

    @GetMapping("/note-form")
    public String displayNoteForm() {
        return "note-form";
    }

    @GetMapping("/available-notes")
    public ModelAndView getAllNotesMethod() {
        List<Note> noteList = noteService.findAllNotes();
        return new ModelAndView("note-display","note",noteList);
    }

    @GetMapping("/filterNotesByTitle")
    public String filterNotesByTitleMethod(@RequestParam("title") String title, Model model) {
        List<Note> filterNotesByTitleResults = noteService.filterNoteByTitle(title);
        model.addAttribute("filterByTitleResults",filterNotesByTitleResults);
        return "note-display";
    }


    /*
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
     */


}
