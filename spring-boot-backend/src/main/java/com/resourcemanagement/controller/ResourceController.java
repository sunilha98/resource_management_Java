package com.resourcemanagement.controller;

import com.resourcemanagement.entity.Resource;
import com.resourcemanagement.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResourceController {
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceRepository.findByIsActiveTrue();
        return ResponseEntity.ok(resources);
    }
    
    @GetMapping("/bench")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public ResponseEntity<List<Resource>> getBenchResources() {
        List<Resource> benchResources = resourceRepository.findByIsActiveTrueAndBenchStatus(Resource.BenchStatus.AVAILABLE);
        return ResponseEntity.ok(benchResources);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        return resourceRepository.findById(id)
                .map(resource -> ResponseEntity.ok().body(resource))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        Resource savedResource = resourceRepository.save(resource);
        return ResponseEntity.ok(savedResource);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource resourceDetails) {
        return resourceRepository.findById(id)
                .map(resource -> {
                    resource.setFirstName(resourceDetails.getFirstName());
                    resource.setLastName(resourceDetails.getLastName());
                    resource.setEmail(resourceDetails.getEmail());
                    resource.setTitle(resourceDetails.getTitle());
                    resource.setLocation(resourceDetails.getLocation());
                    resource.setPractice(resourceDetails.getPractice());
                    resource.setExperience(resourceDetails.getExperience());
                    resource.setSkillsetIds(resourceDetails.getSkillsetIds());
                    resource.setBenchStatus(resourceDetails.getBenchStatus());
                    return ResponseEntity.ok(resourceRepository.save(resource));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}