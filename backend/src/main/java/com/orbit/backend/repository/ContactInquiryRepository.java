package com.orbit.backend.repository;

import com.orbit.backend.entities.ContactInquiry;
import com.orbit.backend.enums.InquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactInquiryRepository extends JpaRepository<ContactInquiry,Long>
{
    List<ContactInquiry> findAllByStatus(InquiryStatus status);
}
