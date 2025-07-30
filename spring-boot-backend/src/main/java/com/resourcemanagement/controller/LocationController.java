package com.resourcemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.entity.Location;
import com.resourcemanagement.service.LocationService;

@RestController
@RequestMapping("/masters/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PROJECT_MANAGER')")
    public Location createLocation(@RequestBody Location location) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return locationService.createLocation(location, username);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PROJECT_MANAGER')")
    public Location updateLocation(@PathVariable Long id,
                                   @RequestBody Location location) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return locationService.updateLocation(id, location, username);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PROJECT_MANAGER')")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok().build();
    }
}
