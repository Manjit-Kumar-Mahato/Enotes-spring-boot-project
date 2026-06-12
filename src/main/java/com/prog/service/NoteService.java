package com.prog.service;

import java.util.List;
import com.prog.entity.Note;
import org.springframework.data.domain.Page;

public interface NoteService {

    Note saveNote(Note note);

    Page<Note> getNotesByUser(Integer userId, int page);

    Note getNoteById(Integer id);

    void deleteNote(Integer id);
}