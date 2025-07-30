package com.resourcemanagement.controller;

import java.util.List;
import java.util.Optional;

import com.resourcemanagement.repository.ProjectRepository;
import com.resourcemanagement.repository.ResourceRepository;
import com.resourcemanagement.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.dto.AllocationDTO;
import com.resourcemanagement.entity.Allocation;
import com.resourcemanagement.entity.Resource;
import com.resourcemanagement.entity.Resource.BenchStatus;
import com.resourcemanagement.repository.AllocationRepository;

@RestController
@RequestMapping("/allocations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AllocationController {

    private final ResourceRepository resourceRepository;

    private final TitleRepository titleRepository;

    private final ProjectRepository projectRepository;
    
    @Autowired
    private AllocationRepository allocationRepository;

    AllocationController(ProjectRepository projectRepository, TitleRepository titleRepository, ResourceRepository resourceRepository) {
        this.projectRepository = projectRepository;
        this.titleRepository = titleRepository;
        this.resourceRepository = resourceRepository;
    }
    
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
    public ResponseEntity<Allocation> createAllocation(@RequestBody AllocationDTO allocationDTO) {
    	
    	Allocation allocation = new Allocation();
    	Optional<Resource> resourceOptional = resourceRepository.findById(allocationDTO.getResourceId());
    	Resource resource = resourceOptional.get();
    	
    	allocation.setProject(projectRepository.findByProjectCode(allocationDTO.getProjectCode()));
    	allocation.setTitle(titleRepository.findByName(allocationDTO.getRole()));
    	allocation.setResource(resource);
    	
    	allocation.setAllocationPercentage(allocationDTO.getAllocationPercent());
    	allocation.setStartDate(allocationDTO.getStartDate());
    	allocation.setEndDate(allocationDTO.getEndDate());
    	
        Allocation savedAllocation = allocationRepository.save(allocation);
        
        int prevPercent = resource.getAllocationPercentage();
        int currentPercent = allocationDTO.getAllocationPercent();
        int totalPercent = prevPercent + currentPercent;
        
        if (totalPercent < 100) {
        	resource.setBenchStatus(BenchStatus.AVAILABLE);
        } else {
        	resource.setBenchStatus(BenchStatus.ALLOCATED);
        }
        resource.setAllocationPercentage(totalPercent);
        resourceRepository.saveAndFlush(resource);
        
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