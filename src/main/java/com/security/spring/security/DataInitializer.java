package com.security.spring.security;

import com.security.spring.model.Client;
import com.security.spring.repository.ClientRepository;
import com.security.spring.repository.RoleRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.security.spring.model.Role;

import java.util.Set;

@Configuration
public class DataInitializer {

    private RoleRepo roleRepository;
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepo roleRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        Role userRole = roleRepository.findByName("ROLE_USER");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }
        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (clientRepository.findByEmail("admin@example.com") == null) {
            Client admin = new Client();
            admin.setFirstName("Admin");
            admin.setLastName("Boss");
            admin.setAge(35);
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(adminRole));
            clientRepository.save(admin);
        }

        if (clientRepository.findByEmail("user@example.com") == null) {
            Client user = new Client();
            user.setFirstName("User");
            user.setLastName("Simple");
            user.setAge(25);
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(Set.of(userRole));
            clientRepository.save(user);
        }
    }
}
