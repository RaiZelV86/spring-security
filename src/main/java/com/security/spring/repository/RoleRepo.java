package com.security.spring.repository;

import com.security.spring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
