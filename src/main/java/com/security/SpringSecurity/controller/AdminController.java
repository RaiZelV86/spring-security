package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("title", "Админ-панель");
        return "admin";
    }
}
