package com.prog.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import com.prog.entity.Note;
import com.prog.entity.User;
import com.prog.repository.UserRepository;
import com.prog.service.NoteService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private NoteService noteService;

	@ModelAttribute
	public void getUser(Principal p, Model m) {
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user", user);
	}

	@GetMapping("/addNotes")
	public String addNotes() {
		return "add_notes";
	}

	@GetMapping("/viewNotes")
	public String viewNotes(
	        @RequestParam(defaultValue = "0") int page,
	        Principal principal,
	        Model model) {

	    String email = principal.getName();
	    User user = userRepo.findByEmail(email);
	    Page<Note> notesPage =
	            noteService.getNotesByUser(user.getId(), page);
	    model.addAttribute("notes", notesPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages",
	            notesPage.getTotalPages());
	    model.addAttribute("totalItems",
	            notesPage.getTotalElements());

	    return "view_notes";
	}

	
	@GetMapping("/editNotes/{id}")
	public String editNote(@PathVariable Integer id,
	                       Model model){
	    model.addAttribute("note",
	            noteService.getNoteById(id));
	    return "edit_notes";
	}
	
	@PostMapping("/saveNote")
	public String saveNote(@ModelAttribute Note note,
	                       Principal principal) {
	    String email = principal.getName();
	    User user = userRepo.findByEmail(email);
	    note.setUser(user);
	    note.setDate(LocalDate.now());
	    noteService.saveNote(note);

	    return "redirect:/user/viewNotes";
	}
	
	@PostMapping("/updateNote")
	public String updateNote(@ModelAttribute Note note,
	                         Principal principal) {
	    String email = principal.getName();
	    User user = userRepo.findByEmail(email);
	    note.setUser(user);
	    Note oldNote = noteService.getNoteById(note.getId());
	    note.setDate(oldNote.getDate());
	    noteService.saveNote(note);

	    return "redirect:/user/viewNotes";
	}
	
	@GetMapping("/deleteNote/{id}")
	public String deleteNote(@PathVariable Integer id){
	    noteService.deleteNote(id);

	    return "redirect:/user/viewNotes";
	}
}