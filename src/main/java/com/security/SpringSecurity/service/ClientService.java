package com.security.SpringSecurity.service;

import com.security.SpringSecurity.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client getClientById(Long id);
    void saveClient(Client client);
    void deleteClientById(Long id);
}
