package org.email_service.service;

import org.email_service.model.Email;
import org.email_service.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final MailSender mailSender;

    @Autowired
    public EmailService(EmailRepository emailRepository, MailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }


    public void sendEmail(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

        Email email = new Email();
        email.setRecipient(recipient);
        email.setSubject(subject);
        email.setBody(body);
        email.setSentOn(LocalDateTime.now());

        emailRepository.save(email);
    }

}
