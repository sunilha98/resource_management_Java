package com.resourcemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
	List<ActivityLog> findTop10ByOrderByTimestampDesc();
}
