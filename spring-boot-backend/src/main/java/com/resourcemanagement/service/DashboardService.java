package com.resourcemanagement.service;

import com.resourcemanagement.dto.DashboardMetricsDTO;
import com.resourcemanagement.repository.ProjectRepository;
import com.resourcemanagement.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    public DashboardMetricsDTO getDashboardMetrics() {
        Long activeProjects = projectRepository.countActiveProjects();
        Long totalResources = resourceRepository.countActiveResources();
        Long benchResources = resourceRepository.countBenchResources();
        
        Long allocatedResources = totalResources - benchResources;
        Integer utilizationRate = totalResources > 0 ? 
            Math.round((allocatedResources.floatValue() / totalResources.floatValue()) * 100) : 0;
        
        return new DashboardMetricsDTO(
            activeProjects.intValue(),
            totalResources.intValue(),
            utilizationRate,
            benchResources.intValue()
        );
    }
}