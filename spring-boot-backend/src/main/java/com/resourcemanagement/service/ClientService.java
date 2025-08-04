package com.resourcemanagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resourcemanagement.dto.ClientRequest;
import com.resourcemanagement.entity.Client;
import com.resourcemanagement.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public void createClient(ClientRequest request) {
		if (clientRepository.existsByCode(request.getCode())) {
			throw new RuntimeException("Client code already exists");
		}

		Client client = new Client();
		client.setName(request.getName());
		client.setCode(request.getCode());
		client.setContactEmail(request.getContactEmail());
		client.setContactPhone(request.getContactPhone());
		client.setIsActive(true);
		client.setCreatedAt(LocalDateTime.now());

		clientRepository.save(client);
	}
}
