package com.resourcemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.activities.ActivityContextHolder;
import com.resourcemanagement.activities.LogActivity;
import com.resourcemanagement.dto.ClientRequest;
import com.resourcemanagement.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@LogActivity(action = "Created Client", module = "Client Management")
	@PostMapping
	public ResponseEntity<String> createClient(@RequestBody ClientRequest request) {
		clientService.createClient(request);

		ActivityContextHolder.setDetail("Client", request.getName());

		return ResponseEntity.ok("Client created successfully");
	}

}
