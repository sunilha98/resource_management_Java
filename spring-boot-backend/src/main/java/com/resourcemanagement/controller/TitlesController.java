package com.resourcemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resourcemanagement.entity.Title;
import com.resourcemanagement.repository.TitleRepository;

@RestController
@RequestMapping("/titles")
public class TitlesController {
	
	@Autowired
	private TitleRepository titleRepository;

	@GetMapping
	public List<Title> getAllTitles() {
	    return titleRepository.findAll();
	}

}
