package app.service;

import app.model.Notification;
import app.model.NotificationPreference;
import app.model.NotificationStatus;
import app.repository.NotificationRepository;
import app.repository.NotificationPreferenceRepository;
import app.web.dto.NotificationRequest;
import app.web.dto.UpsertNotificationPreference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class NotificationService {

    private final NotificationPreferenceRepository preferenceRepository;
    private final NotificationRepository notificationRepository;
    private final MailSender mailSender;

    @Autowired
    public NotificationService(NotificationPreferenceRepository notificationPreferenceRepository, NotificationRepository notificationRepository, MailSender mailSender) {
        this.preferenceRepository = notificationPreferenceRepository;
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }


    public Notification sendNotification(NotificationRequest notificationRequest) {

        UUID userId = notificationRequest.getUserId();
        NotificationPreference userPreference = getPreferenceByUserId(userId);

        if (!userPreference.isEnabled()) {
            throw new IllegalArgumentException("User with id %s does not allow to receive notifications".formatted(userId));
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userPreference.getContactInfo());
        message.setSubject(notificationRequest.getSubject());
        message.setText(notificationRequest.getBody());

        Notification notification = Notification.builder()
                .subject(notificationRequest.getSubject())
                .body(notificationRequest.getBody())
                .createdOn(LocalDateTime.now())
                .userId(userId)
                .deleted(false)
                .build();

        try {
            mailSender.send(message);
            notification.setStatus(NotificationStatus.SUCCEEDED);

        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            log.warn("Failed to send notification to {} due to {}", userPreference.getContactInfo(), e.getMessage());
        }

        return notificationRepository.save(notification);
    }

    public NotificationPreference upsertPreference(UpsertNotificationPreference dto) {

        Optional<NotificationPreference> userNotificationPreference = preferenceRepository.findByUserId(dto.getUserId());

        if(userNotificationPreference.isPresent()) {
            NotificationPreference notificationPreference = userNotificationPreference.get();

            notificationPreference.setContactInfo(dto.getContactInfo());
            notificationPreference.setEnabled(dto.isNotificationEnabled());
            notificationPreference.setUpdatedOn(LocalDateTime.now());

            return preferenceRepository.save(notificationPreference);
        }

        NotificationPreference notificationPreference = NotificationPreference.builder()
                .userId(dto.getUserId())
                .isEnabled(dto.isNotificationEnabled())
                .contactInfo(dto.getContactInfo())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        return preferenceRepository.save(notificationPreference);
    }

    public NotificationPreference getPreferenceByUserId(UUID userId) {

        return preferenceRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("Notification Preference for user id %s was not found.".formatted(userId)));

    }

    public List<Notification> getNotificationHistory(UUID userId) {

        return notificationRepository.findAllByUserIdAndDeletedIsFalse(userId);
    }
}
