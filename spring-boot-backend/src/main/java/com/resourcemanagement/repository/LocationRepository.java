package com.resourcemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resourcemanagement.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	
}
