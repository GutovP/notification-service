package app.web;

import app.repository.EmailRepository;
import app.service.EmailService;
import app.web.dto.EmailRequest;
import app.web.dto.EmailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final EmailRepository emailRepository;

    public NotificationController(EmailService emailService, EmailRepository emailRepository) {
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    @GetMapping("/emails-history")
    public ResponseEntity<List<EmailResponse>> getEmailHistory() {

        List<EmailResponse> emails = emailRepository.findAll().stream()
                .map(EmailResponse::fromEmailEntity)
                .toList();

        return ResponseEntity.ok(emails);
    }

    @PostMapping("/emails")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest) {

        emailService.sendEmail(emailRequest.getRecipient(), emailRequest.getSubject(), emailRequest.getBody());

        return ResponseEntity.ok().body(Map.of("message", "Email sent successfully"));

    }
}
