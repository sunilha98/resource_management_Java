package com.resourcemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.ShiftTiming;

public interface ShiftTimingRepository extends JpaRepository<ShiftTiming, Long> {
    boolean existsByName(String name);
}
