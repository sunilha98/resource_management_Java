package com.resourcemanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.FulfillmentRequest;

public interface FulfillmentRequestRepository extends JpaRepository<FulfillmentRequest, UUID> {
}
