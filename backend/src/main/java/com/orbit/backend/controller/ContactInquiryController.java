package com.orbit.backend.controller;

import com.orbit.backend.dto.ContactInquiryRequest;
import com.orbit.backend.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactInquiryController {
private final ContactService service;

@PostMapping
    public ResponseEntity<String> submit(@Valid @RequestBody ContactInquiryRequest request){
    return ResponseEntity.status(201).body(service.submitQuery(request));
}
}
