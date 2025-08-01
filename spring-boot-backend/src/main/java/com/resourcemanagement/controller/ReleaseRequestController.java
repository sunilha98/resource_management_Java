package com.resourcemanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.dto.ReleaseRequestDTO;
import com.resourcemanagement.entity.ReleaseRequest;
import com.resourcemanagement.service.ReleaseRequestService;

@RestController
@RequestMapping("/release-requests")
public class ReleaseRequestController {

    @Autowired
    private ReleaseRequestService releaseRequestService;

    @PostMapping
    public ResponseEntity<ReleaseRequest> create(@RequestBody ReleaseRequestDTO dto) {
        ReleaseRequest saved = releaseRequestService.createReleaseRequest(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ReleaseRequest>> getAll() {
        return ResponseEntity.ok(releaseRequestService.getAllRequests());
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        try {
            releaseRequestService.updateStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status");
        }
    }

}
