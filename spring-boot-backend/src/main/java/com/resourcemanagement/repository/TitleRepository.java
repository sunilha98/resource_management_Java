package com.resourcemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resourcemanagement.entity.Title;

public interface TitleRepository extends JpaRepository<Title, Long>{

	Title findByName(String name);
}
