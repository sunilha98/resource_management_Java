package com.resourcemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.dto.SpendTrackingDTO;
import com.resourcemanagement.service.ReportsService;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportsController {
	
	@Autowired
	private ReportsService reportsService;

	@GetMapping("/spend-tracking")
	public ResponseEntity<List<SpendTrackingDTO>> getSpendTrackingReport() {
	    return ResponseEntity.ok(reportsService.getSpendTrackingReport());
	}

}
