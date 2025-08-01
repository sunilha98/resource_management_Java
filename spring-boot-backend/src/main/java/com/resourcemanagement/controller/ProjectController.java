package com.resourcemanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.resourcemanagement.dto.ProjectsDTO;
import com.resourcemanagement.entity.Project;
import com.resourcemanagement.repository.ProjectRepository;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
	public ResponseEntity<List<ProjectsDTO>> getAllProjects() {
		List<Project> projects = projectRepository.findAllOrderByCreatedAtDesc();
		return ResponseEntity.ok(projects.stream().map(this::mapToSummaryDTO).collect(Collectors.toList()));
	}

	private ProjectsDTO mapToSummaryDTO(Project project) {
		ProjectsDTO dto = new ProjectsDTO();
		dto.setId(project.getId());
		dto.setProjectCode(project.getProjectCode());
		dto.setName(project.getName());
		dto.setClientName(project.getClient().getName());
		dto.setPractice(project.getPractice().getName());
		dto.setStatus(project.getStatus().name());
		dto.setStartDate(project.getStartDate());
		dto.setEndDate(project.getEndDate());
		return dto;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
	public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
		return projectRepository.findById(id).map(project -> ResponseEntity.ok().body(project))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT')")
	public ResponseEntity<Project> createProject(@RequestBody Project project) {
		Project savedProject = projectRepository.save(project);
		return ResponseEntity.ok(savedProject);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('RMT') or hasRole('PROJECT_MANAGER')")
	public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
		return projectRepository.findById(id).map(project -> {
			project.setName(projectDetails.getName());
			project.setDescription(projectDetails.getDescription());
			project.setStatus(projectDetails.getStatus());
			project.setStartDate(projectDetails.getStartDate());
			project.setEndDate(projectDetails.getEndDate());
			project.setBudget(projectDetails.getBudget());
			project.setProgress(projectDetails.getProgress());
			return ResponseEntity.ok(projectRepository.save(project));
		}).orElse(ResponseEntity.notFound().build());
	}
}