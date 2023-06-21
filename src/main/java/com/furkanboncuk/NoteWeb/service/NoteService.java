package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.entity.Note;
import com.furkanboncuk.NoteWeb.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository theNoteRepository) {
        this.noteRepository = theNoteRepository;
    }



}
