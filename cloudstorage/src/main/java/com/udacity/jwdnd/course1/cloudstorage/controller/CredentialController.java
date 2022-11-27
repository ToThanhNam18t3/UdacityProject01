package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId){
        credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }

    @PostMapping("/insertCredential")
    public String insertCredential(Authentication authen, CredentialDto credentialDto){
        credentialService.insertCredential(authen, credentialDto);
        return "redirect:/home";
    }

    @PostMapping("/updateCredential")
    public String updateCredential(Authentication authen, CredentialDto credentialDto){
        credentialService.updateCredential(authen, credentialDto);
        return "redirect:/home";
    }


}
