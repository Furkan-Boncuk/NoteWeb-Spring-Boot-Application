package com.furkanboncuk.NoteWeb.service;

import com.furkanboncuk.NoteWeb.repository.NoteRepository;

public class NoteServiceImpl extends NoteService{

    public NoteServiceImpl(NoteRepository theNoteRepository) {
        super(theNoteRepository);
    }


}
