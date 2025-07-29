package com.resourcemanagement.repository;

import com.resourcemanagement.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    
    List<Allocation> findByStatus(Allocation.AllocationStatus status);
    
    List<Allocation> findByProjectId(Long projectId);
    
    List<Allocation> findByResourceId(Long resourceId);
}