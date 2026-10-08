package com.orbit.backend.service;

import com.orbit.backend.dto.CertificateVerifyResponse;
import com.orbit.backend.dto.CreateCertificateRequest;
import com.orbit.backend.entities.InternCertificate;
import com.orbit.backend.enums.CertificateStatus;
import com.orbit.backend.repository.InternCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final InternCertificateRepository repository;
    public CertificateVerifyResponse verify(String code){
        InternCertificate certificate = repository.findByCertificateCode(code.toUpperCase())
                .orElseThrow(()-> new RuntimeException("Certificate not found with code : "+code));
        return new CertificateVerifyResponse(
                certificate.getName(),
                certificate.getRole(),
                certificate.getDepartment(),
                certificate.getStartDate(),
                certificate.getEndDate(),
                certificate.getDuration(),
                certificate.getIssuedBy(),
                certificate.getStatus().name(),
                certificate.getCertificateCode()
        );
    }
    public String createCertificate(CreateCertificateRequest request){
        if (repository.findByCertificateCode(request.certificateCode().toUpperCase()).isPresent()){
            throw new RuntimeException("Certificate code already exists");
        }
        InternCertificate cert = new InternCertificate();
        cert.setName(request.name());
        cert.setRole(request.role());
        cert.setDepartment(request.department());
        cert.setDuration(request.duration());
        cert.setStartDate(request.startDate());
        cert.setEndDate(request.endDate());
        cert.setCertificateCode(request.certificateCode().toUpperCase());
        cert.setStatus(CertificateStatus.COMPLETED);
        repository.save(cert);
        return "Certificate created successfully with code: " +
                request.certificateCode().toUpperCase();
    }
}
