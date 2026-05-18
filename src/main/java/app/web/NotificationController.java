package app.web;

import app.service.NotificationService;
import app.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;

    }

    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationRequest notificationRequest) {

        notificationService.sendNotification(notificationRequest);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping("/restockAlert")
    public ResponseEntity<Void> sendRestockAlert(@RequestBody RestockAlertRequest stockAlertRequest) {

       notificationService.sendRestockAlert(stockAlertRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
