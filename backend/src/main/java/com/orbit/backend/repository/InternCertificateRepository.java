package com.orbit.backend.repository;

import com.orbit.backend.entities.InternCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternCertificateRepository extends JpaRepository<InternCertificate,Long> {

    Optional<InternCertificate> findByCertificateCode(String certificateCode);
}
