package app.repository;

import app.model.RestockAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestockAlertRepository extends JpaRepository<RestockAlert, UUID> {
}
