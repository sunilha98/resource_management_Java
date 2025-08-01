package com.resourcemanagement.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.dto.FulfillmentCreationRequestDTO;
import com.resourcemanagement.dto.FulfillmentRequestDTO;
import com.resourcemanagement.entity.FulfillmentRequest;
import com.resourcemanagement.entity.Skillset;
import com.resourcemanagement.repository.FulfillmentRequestRepository;
import com.resourcemanagement.repository.LocationRepository;
import com.resourcemanagement.repository.ProjectRepository;
import com.resourcemanagement.repository.ShiftTimingRepository;
import com.resourcemanagement.repository.SkillSetRepository;
import com.resourcemanagement.repository.TitleRepository;

@RestController
@RequestMapping("/fulfillment-requests")
public class FulfillmentRequestController {

	@Autowired
	private FulfillmentRequestRepository repository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TitleRepository titleRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ShiftTimingRepository shiftRepository;

	@Autowired
	private SkillSetRepository skillsetRepository;

	@GetMapping
	public ResponseEntity<List<FulfillmentRequestDTO>> getAllRequests() {
		List<FulfillmentRequest> requests = repository.findAll();

		List<FulfillmentRequestDTO> dtos = requests.stream().map(req -> {
			FulfillmentRequestDTO dto = new FulfillmentRequestDTO();
			dto.setId(req.getId());
			dto.setProjectName(req.getProject().getName());
			dto.setTitle(req.getTitle().getName());
			dto.setSkills(req.getSkillsets().stream().map(Skillset::getName).toList());
			dto.setLocation(req.getLocation().getName());
			dto.setStatus(req.getStatus().name());
			dto.setExpectedClosure(req.getExpectedClosure());
			dto.setNotes(req.getNotes());
			dto.setExperience(req.getExperience());
			dto.setPositions(req.getPositions());
			dto.setShift(req.getShift().getName());
			return dto;
		}).toList();

		return ResponseEntity.ok(dtos);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStatusAndNotes(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
		Optional<FulfillmentRequest> optional = repository.findById(id);
		if (optional.isEmpty())
			return ResponseEntity.notFound().build();

		FulfillmentRequest request = optional.get();
		if (updates.containsKey("status")) {
			request.setStatus(FulfillmentRequest.Status.valueOf((String) updates.get("status")));
		}
		if (updates.containsKey("notes")) {
			request.setNotes((String) updates.get("notes"));
		}

		repository.save(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<?> createFulfillmentRequest(@RequestBody FulfillmentCreationRequestDTO dto) {
		FulfillmentRequest request = new FulfillmentRequest();

		request.setProject(projectRepository.findByProjectCode(dto.getProjectCode()));
		request.setTitle(titleRepository.findById(dto.getTitleId()).orElse(null));
		request.setLocation(locationRepository.findById(dto.getLocationId()).orElse(null));
		request.setShift(shiftRepository.findById(dto.getShiftId()).orElse(null));
		request.setExperience(dto.getExperience());
		request.setPositions(dto.getPositions());
		request.setExpectedClosure(dto.getExpectedClosure());
		request.setNotes(dto.getNotes());
		request.setStatus(FulfillmentRequest.Status.OPEN);

		// Set skillsets
		List<Skillset> skills = skillsetRepository.findAllById(dto.getSkillsetIds());
		request.setSkillsets(skills);

		FulfillmentRequest saved = repository.save(request);
		return ResponseEntity.ok(saved);
	}

}
