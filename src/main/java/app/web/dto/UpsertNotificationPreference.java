package app.web.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class UpsertNotificationPreference {

    private UUID userId;

    private boolean isNotificationEnabled;

    private NotificationTypeRequest type;

    private String contactInfo;
}
