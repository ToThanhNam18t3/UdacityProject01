package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signUpPage() {
        return "signup";
    }

    @PostMapping()
    public String signUpNewUser(Model model, User user) {
        String signupError = null;

        if (!userService.checkUserAvailable(user.getUsername())) {
            signupError = "The username already exists ! Please choose another name !";
        }

        if (signupError == null) {int createNewUser = userService.createUser(user);
            if (createNewUser < 0) {
                signupError = "There was an error while you sign up. Please try again !";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }

}
