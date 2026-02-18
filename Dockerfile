FROM amazoncorretto:21

COPY target/notification-service-*.jar notif-svc.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Demail.username=${EMAIL_USERNAME}", "-Demail.password=${EMAIL_PASSWORD}", "-jar", "notif-svc.jar"]

EXPOSE 8081