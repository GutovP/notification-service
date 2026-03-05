package app.event;


import app.event.payload.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegisteredEventConsumer {

    @KafkaListener(topics = "user-registered-event.v1", groupId = "notification-service")
    public void consumeUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {

        log.info("Successfully consumed registered event for user [%s]".formatted(userRegisteredEvent.getUserId()));

    }
}
