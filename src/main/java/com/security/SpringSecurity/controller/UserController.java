package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client currentUser = (Client) auth.getPrincipal();

        model.addAttribute("client", currentUser);
        return "user";
    }
}
