package com.security.SpringSecurity.controller;

import com.security.SpringSecurity.model.Client;
import com.security.SpringSecurity.model.Role;
import com.security.SpringSecurity.repository.RoleRepo;
import com.security.SpringSecurity.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientRestController {

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private RoleRepo roleRepo;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.findAll();
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            if (client != null) {
                return ResponseEntity.ok(client);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        try {
            // Если указана роль, добавляем её
            if (client.getRoles() != null && !client.getRoles().isEmpty()) {
                Set<Role> roles = client.getRoles();
                for (Role role : roles) {
                    Role existingRole = roleRepo.findByName(role.getName());
                    if (existingRole != null) {
                        client.getRoles().remove(role);
                        client.getRoles().add(existingRole);
                    }
                }
            }
            
            clientService.saveClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        try {
            Client existingClient = clientService.getClientById(id);
            if (existingClient == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Обновляем поля
            existingClient.setUserName(client.getUserName());
            existingClient.setLastName(client.getLastName());
            existingClient.setAge(client.getAge());
            existingClient.setEmail(client.getEmail());
            if (client.getPassword() != null && !client.getPassword().isEmpty()) {
                existingClient.setPassword(client.getPassword());
            }
            
            // Обновляем роли
            if (client.getRoles() != null) {
                existingClient.setRoles(client.getRoles());
            }
            
            clientService.saveClient(existingClient);
            return ResponseEntity.ok(existingClient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            System.out.println("Попытка удаления клиента с ID: " + id);
            Client client = clientService.getClientById(id);
            if (client == null) {
                System.out.println("Клиент с ID " + id + " не найден");
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("Удаляем клиента: " + client.getUserName() + " " + client.getLastName());
            clientService.deleteClientById(id);
            System.out.println("Клиент успешно удален");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении клиента с ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            List<Role> roles = roleRepo.findAll();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
