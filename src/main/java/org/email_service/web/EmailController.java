package org.email_service.web;

import org.email_service.model.Email;
import org.email_service.repository.EmailRepository;
import org.email_service.service.EmailService;
import org.email_service.web.dto.EmailRequest;
import org.email_service.web.dto.EmailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/email")
public class EmailController {

    private final EmailService emailService;
    private final EmailRepository emailRepository;

    public EmailController(EmailService emailService, EmailRepository emailRepository) {
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<List<EmailResponse>> getEmailHistory() {

        List<EmailResponse> emails = emailRepository.findAll().stream()
                .map(EmailResponse::fromEmailEntity)
                .toList();

        return ResponseEntity.ok(emails);
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest) {

        emailService.sendEmail(emailRequest.getRecipient(), emailRequest.getSubject(), emailRequest.getBody());

        return ResponseEntity.ok().body(Map.of("message", "Email sent successfully"));

    }
}
