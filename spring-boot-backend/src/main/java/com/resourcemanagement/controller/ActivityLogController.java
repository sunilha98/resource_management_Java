package com.resourcemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.entity.ActivityLog;
import com.resourcemanagement.service.ActivityLogService;

@RestController
@RequestMapping("/activity")
public class ActivityLogController {

	@Autowired
	private ActivityLogService activityLogService;

	@GetMapping("/recent")
	public ResponseEntity<List<ActivityLog>> getRecentActivities() {
		return ResponseEntity.ok(activityLogService.getRecentActivities());
	}
}
