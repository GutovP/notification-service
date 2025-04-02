package org.email_service.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {

    @NotBlank
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;
}
