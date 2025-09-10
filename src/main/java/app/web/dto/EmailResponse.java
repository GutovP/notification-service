package app.web.dto;

import app.model.Email;

import java.time.LocalDateTime;


public record EmailResponse (String recipient, String subject, LocalDateTime sentOn) {

    public static EmailResponse fromEmailEntity(Email email) {
        return new EmailResponse(email.getRecipient(), email.getSubject(), email.getSentOn());
    }
}
