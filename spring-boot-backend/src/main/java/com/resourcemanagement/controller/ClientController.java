package com.resourcemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.dto.ClientRequest;
import com.resourcemanagement.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody ClientRequest request) {
        clientService.createClient(request);
        return ResponseEntity.ok("Client created successfully");
    }
    
//    TODO list all clients needed on UI also
}
