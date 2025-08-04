package com.resourcemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.resourcemanagement.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

	List<Resource> findByIsActiveTrue();

	List<Resource> findByIsActiveTrueAndBenchStatus(Resource.BenchStatus benchStatus);

	@Query("SELECT COUNT(r) FROM Resource r WHERE r.isActive = true")
	Long countActiveResources();

	@Query("SELECT COUNT(r) FROM Resource r WHERE r.isActive = true AND r.benchStatus = 'AVAILABLE'")
	Long countBenchResources();
}