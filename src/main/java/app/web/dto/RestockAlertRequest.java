package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestockAlertRequest {

    @NotBlank
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;
}
