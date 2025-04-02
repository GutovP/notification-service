package org.email_service.web.dto;

import org.email_service.model.Email;

import java.time.LocalDateTime;


public record EmailResponse (String recipient, String subject, LocalDateTime sentOn) {

    public static EmailResponse fromEmailEntity(Email email) {
        return new EmailResponse(email.getRecipient(), email.getSubject(), email.getSentOn());
    }
}
