package com.resourcemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.resourcemanagement.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByStatus(Project.ProjectStatus status);

	@Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'IN_FLIGHT'")
	Long countActiveProjects();

	@Query("SELECT p FROM Project p ORDER BY p.createdAt DESC")
	List<Project> findAllOrderByCreatedAtDesc();

	Project findByProjectCode(String projectCode);
}