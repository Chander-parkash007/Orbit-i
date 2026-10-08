package com.orbit.backend.controller;

import com.orbit.backend.dto.CertificateVerifyResponse;
import com.orbit.backend.dto.CreateCertificateRequest;
import com.orbit.backend.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService service;

    @GetMapping("/verify/{code}")
    public ResponseEntity<CertificateVerifyResponse> verify(@PathVariable String code){
        return ResponseEntity.ok(service.verify(code));
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> create(@Valid @RequestBody CreateCertificateRequest request){
        return ResponseEntity.status(201).body(service.createCertificate(request));
    }
}
