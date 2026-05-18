package app.service;

import app.model.RestockAlert;
import app.repository.RestockAlertRepository;
import app.web.dto.NotificationRequest;
import app.web.dto.RestockAlertRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@Service
public class NotificationService {

    private final RestockAlertRepository stockAlertRepository;
    private final MailSender mailSender;
    @Value("${app.mail.from:contact@gutov.net}")
    private String mailFrom;
    @Value("${app.mail.to:petar.gutov@gmail.com}")
    private String mailTo;

    @Autowired
    public NotificationService(RestockAlertRepository stockAlertRepository, MailSender mailSender) {
        this.stockAlertRepository = stockAlertRepository;
        this.mailSender = mailSender;
    }


    public void sendNotification(NotificationRequest notificationRequest) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Neue Kontaktanfrage von " + notificationRequest.getName());
        message.setFrom(mailFrom);
        message.setTo(mailTo);
        message.setReplyTo(notificationRequest.getEmail());

        String phone = (notificationRequest.getPhoneNumber() != null && !notificationRequest.getPhoneNumber().isBlank())
                ? notificationRequest.getPhoneNumber()
                : "Nicht angegeben";

        String emailText = "Du hast eine neue Nachricht über das Kontaktformular erhalten:\n\n" +
                "Name: " + notificationRequest.getName() + "\n" +
                "E-Mail: " + notificationRequest.getEmail() + "\n" +
                "Telefon: " + phone + "\n\n" +
                "Nachricht:\n" + notificationRequest.getMessage();

        message.setText(emailText);

        mailSender.send(message);
    }

    public void sendRestockAlert(RestockAlertRequest restockAlertRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(restockAlertRequest.getRecipient());
        message.setSubject(restockAlertRequest.getSubject());
        message.setText(restockAlertRequest.getBody());

        mailSender.send(message);

        RestockAlert restockAlert = RestockAlert.builder()
                .recipient(restockAlertRequest.getRecipient())
                .subject(restockAlertRequest.getSubject())
                .body(restockAlertRequest.getBody())
                .sentOn(LocalDateTime.now())
                .build();

        stockAlertRepository.save(restockAlert);
    }
}
