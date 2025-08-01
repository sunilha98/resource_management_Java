package com.resourcemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.ReleaseRequest;

public interface ReleaseRequestRepository extends JpaRepository<ReleaseRequest, Long> {
    List<ReleaseRequest> findByProjectId(Long projectId);
}
