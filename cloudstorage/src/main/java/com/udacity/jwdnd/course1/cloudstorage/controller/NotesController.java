package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {

    private NotesService noteService;

    public NotesController(NotesService noteService){
        this.noteService = noteService;
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId){
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }

    @PostMapping("/insertNote")
    public String insertNote(Authentication authen, NoteForm noteForm){
        noteService.addNote(authen, noteForm);
        return "redirect:/home";
    }

    @PostMapping("/updateNote")
    public String updateNote(Authentication authen, NoteForm noteForm) {
        noteService.updateNote(authen, noteForm);
        return "redirect:/home";
    }

}
