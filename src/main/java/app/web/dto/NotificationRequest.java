package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private String phoneNumber;

    @NotBlank
    private String message;
}
