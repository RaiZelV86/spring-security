package com.security.spring.service;

import com.security.spring.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client getClientById(Long id);
    void saveClient(Client client);
    void deleteClientById(Long id);
}
