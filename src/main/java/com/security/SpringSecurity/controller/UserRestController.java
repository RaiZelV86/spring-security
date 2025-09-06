package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import com.security.SpringSecurity.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/current")
    public ResponseEntity<Client> getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String email = auth.getName(); // В Spring Security это обычно email/username
                Client currentUser = clientService.findAll().stream()
                    .filter(client -> client.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
                
                if (currentUser != null) {
                    return ResponseEntity.ok(currentUser);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Для отладки
            return ResponseEntity.status(500).build();
        }
    }
}
