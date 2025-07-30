package com.resourcemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.Practice;

public interface PracticeRepository extends JpaRepository<Practice, Long> {

	Practice findByName(String name);
}
