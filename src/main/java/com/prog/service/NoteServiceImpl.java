package com.prog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prog.entity.Note;
import com.prog.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepo;

    @Override
    public Note saveNote(Note note) {
        return noteRepo.save(note);
    }

    @Override
    public Page<Note> getNotesByUser(Integer userId, int page) {
        Pageable pageable = PageRequest.of(page, 5);

        return noteRepo.findByUserId(userId, pageable);
    }

    @Override
    public Note getNoteById(Integer id) {
        return noteRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteNote(Integer id) {
        noteRepo.deleteById(id);
    }
}
