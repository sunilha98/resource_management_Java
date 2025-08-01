package com.resourcemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.dto.ShiftTimingDTO;
import com.resourcemanagement.entity.ShiftTiming;
import com.resourcemanagement.repository.ShiftTimingRepository;

@RestController
@RequestMapping("/shifts")
public class ShiftTimingController {

    @Autowired
    private ShiftTimingRepository shiftRepo;

    @PostMapping
    public ResponseEntity<?> createShift(@RequestBody ShiftTimingDTO dto) {
        if (shiftRepo.existsByName(dto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Shift already exists");
        }

        ShiftTiming shift = new ShiftTiming();
        shift.setName(dto.getName());
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());

        shiftRepo.save(shift);
        return ResponseEntity.ok("Shift created successfully");
    }

    @GetMapping
    public ResponseEntity<List<ShiftTiming>> getAllShifts() {
        return ResponseEntity.ok(shiftRepo.findAll());
    }
}
