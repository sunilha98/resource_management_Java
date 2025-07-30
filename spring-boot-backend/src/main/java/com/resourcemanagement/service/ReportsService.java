package com.resourcemanagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resourcemanagement.dto.SpendTrackingDTO;
import com.resourcemanagement.entity.Project;
import com.resourcemanagement.entity.Project.ProjectStatus;
import com.resourcemanagement.repository.ProjectRepository;

@Service
public class ReportsService {

	@Autowired
	private ProjectRepository projectRepository;

	public List<SpendTrackingDTO> getSpendTrackingReport() {
		List<Project> projects = projectRepository.findByStatus(ProjectStatus.IN_FLIGHT);

		return projects.stream().map(p -> {
			BigDecimal planned = Optional.ofNullable(p.getBudget()).orElse(BigDecimal.ZERO);
			BigDecimal actual = Optional.ofNullable(p.getActualCost()).orElse(BigDecimal.ZERO);
			BigDecimal variance = actual.subtract(planned);

			return new SpendTrackingDTO(p.getClient().getName(), p.getName(), planned.doubleValue(),
					actual.doubleValue(), variance.doubleValue());
		}).collect(Collectors.toList());
	}

}
