package com.resourcemanagement.controller;

import com.resourcemanagement.entity.Allocation;
import com.resourcemanagement.repository.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AllocationController {
    
    @Autowired
    private AllocationRepository allocationRepository;
    
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
    public ResponseEntity<List<Allocation>> getAllAllocations() {
        List<Allocation> allocations = allocationRepository.findAll();
        return ResponseEntity.ok(allocations);
    }
    
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
    public ResponseEntity<List<Allocation>> getAllocationsByProject(@PathVariable Long projectId) {
        List<Allocation> allocations = allocationRepository.findByProjectId(projectId);
        return ResponseEntity.ok(allocations);
    }
    
    @GetMapping("/resource/{resourceId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public ResponseEntity<List<Allocation>> getAllocationsByResource(@PathVariable Long resourceId) {
        List<Allocation> allocations = allocationRepository.findByResourceId(resourceId);
        return ResponseEntity.ok(allocations);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public ResponseEntity<Allocation> createAllocation(@RequestBody Allocation allocation) {
        Allocation savedAllocation = allocationRepository.save(allocation);
        return ResponseEntity.ok(savedAllocation);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public ResponseEntity<Allocation> updateAllocation(@PathVariable Long id, @RequestBody Allocation allocationDetails) {
        return allocationRepository.findById(id)
                .map(allocation -> {
                    allocation.setAllocationPercentage(allocationDetails.getAllocationPercentage());
                    allocation.setStartDate(allocationDetails.getStartDate());
                    allocation.setEndDate(allocationDetails.getEndDate());
                    allocation.setStatus(allocationDetails.getStatus());
                    return ResponseEntity.ok(allocationRepository.save(allocation));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}