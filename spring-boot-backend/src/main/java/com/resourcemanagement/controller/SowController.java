package com.resourcemanagement.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resourcemanagement.dto.SowUploadRequest;
import com.resourcemanagement.service.SowService;

@RestController
@RequestMapping("/sows")
public class SowController {

	@Autowired
    private SowService sowService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSow(
            @RequestParam("file") MultipartFile file,
            @RequestParam("priority") String priority,
            @RequestParam("clientName") String clientName,
            @RequestParam("projectName") String projectName,
            @RequestParam("positions") String positionsJson
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<SowUploadRequest.PositionRequest> positions = Arrays.asList(
                mapper.readValue(positionsJson, SowUploadRequest.PositionRequest[].class)
        );

        SowUploadRequest request = new SowUploadRequest();
        request.setPriority(priority);
        request.setClientName(clientName);
        request.setProjectName(projectName);
        request.setPositions(positions);

        sowService.handleSowUpload(file, request);
        return ResponseEntity.ok("SoW and Project created successfully!");
    }
}
