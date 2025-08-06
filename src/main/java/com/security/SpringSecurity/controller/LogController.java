package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import com.security.SpringSecurity.model.Role;
import com.security.SpringSecurity.repository.RoleRepo;
import com.security.SpringSecurity.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class LogController {

    private final RoleRepo roleRepo;
    private final ClientService clientService;

    @Autowired
    public LogController(RoleRepo roleRepo, ClientService clientService) {
        this.roleRepo = roleRepo;
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("client") Client client,
                                      BindingResult result) {
        if (result.hasErrors()) return "register";
        // Назначаем роль USER
        Role userRole = roleRepo.findByName("ROLE_USER");
        client.getRoles().add(userRole);
        clientService.saveClient(client);
        return "redirect:/login?registered";
    }
}
