package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private NotesService noteService;
    private FileService fileService;
    private CredentialService credentialService;

    public HomeController(NotesService noteService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String showHomePage(Authentication authen, Model model, NoteForm noteForm, CredentialDto credentialDto){
        model.addAttribute("notes", noteService.getAllListNotes(authen));
        model.addAttribute("files", fileService.getAllFilesByUserId(authen));
        model.addAttribute("credentials", credentialService.getAllCredentialsByUserId(authen));
        return "home";
    }

}
