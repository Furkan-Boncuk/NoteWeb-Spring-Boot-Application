package com.furkanboncuk.NoteWeb.repository;

import com.furkanboncuk.NoteWeb.modal.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NotePaginationRepository extends PagingAndSortingRepository<Note,Long> {
    List<Note> findNoteByTitleContaining(String title, Pageable pageable);

}
