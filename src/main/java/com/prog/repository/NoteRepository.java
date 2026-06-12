package com.prog.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prog.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteRepository extends JpaRepository<Note,Integer>{

	  Page<Note> findByUserId(Integer userId, Pageable pageable);

}