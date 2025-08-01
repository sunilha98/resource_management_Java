package com.resourcemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.ProjectStatusUpdate;

public interface ProjectStatusUpdateRepository extends JpaRepository<ProjectStatusUpdate, Long> {
	List<ProjectStatusUpdate> findByProject_Id(Long projectId);
}
