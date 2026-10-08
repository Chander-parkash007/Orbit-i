package com.orbit.backend.service;

import com.orbit.backend.dto.ContactInquiryRequest;
import com.orbit.backend.entities.ContactInquiry;
import com.orbit.backend.enums.InquiryStatus;
import com.orbit.backend.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactInquiryRepository repository;
    private final JavaMailSender mailSender;

    public String submitQuery(ContactInquiryRequest request){
        ContactInquiry contactInquiry = new ContactInquiry();
        contactInquiry.setName(request.name());
        contactInquiry.setEmail(request.email());
        contactInquiry.setCompany(request.company());
        contactInquiry.setService(request.service());
        contactInquiry.setMessage(request.message());
        contactInquiry.setStatus(InquiryStatus.NEW);

        repository.save(contactInquiry);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("contactus@orbit-i.tech");
            mailMessage.setFrom("contactus@orbit-i.tech");
            mailMessage.setSubject("New Inquiry from " + request.name());
            mailMessage.setText(
                    "New contact inquiry received:\n\n" +
                            "Name: " + request.name() + "\n" +
                            "Email: " + request.email() + "\n" +
                            "Company: " + request.company() + "\n" +
                            "Service: " + request.service() + "\n\n" +
                            "Message:\n" + request.message()
            );
            mailSender.send(mailMessage);
        }catch (Exception e){
            System.out.println("Mail failed to sent : "+e.getMessage());
        }
        return "Thank you for reaching out. We will contact you within 24 hours.";
    }
}
