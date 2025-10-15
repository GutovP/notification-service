package app.web.dto;

import lombok.Data;

@Data
public class RestockAlertRequest {

    private String recipient;

    private String subject;

    private String body;
}
