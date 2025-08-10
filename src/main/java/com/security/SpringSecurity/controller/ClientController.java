package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import com.security.SpringSecurity.model.Role;
import com.security.SpringSecurity.repository.RoleRepo;
import com.security.SpringSecurity.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private RoleRepo roleRepo;

    @GetMapping
    public String showClientList(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "form";
    }

    @PostMapping("/save")
    public String saveClient(@Valid @ModelAttribute("client") Client client,
                             BindingResult result,
                             @RequestParam(value = "role", required = false) String roleName) {
        if (result.hasErrors()) return "form";
        
        // Если указана роль, добавляем её
        if (roleName != null && !roleName.isEmpty()) {
            Role role = roleRepo.findByName(roleName);
            if (role != null) {
                client.getRoles().add(role);
            }
        }
        
        clientService.saveClient(client);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return "redirect:/admin";
    }
}
