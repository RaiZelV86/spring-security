package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import com.security.SpringSecurity.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ClientService clientService;

    @Autowired
    public AdminController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("title", "Admin Panel");
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("newClient", new Client());
        return "admin";
    }
}
