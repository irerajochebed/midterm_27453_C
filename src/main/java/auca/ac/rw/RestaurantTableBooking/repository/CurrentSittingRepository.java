package auca.ac.rw.RestaurantTableBooking.repository;

import auca.ac.rw.RestaurantTableBooking.modal.CurrentSitting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CurrentSittingRepository extends JpaRepository<CurrentSitting, UUID> {

    // existsBy → checks if sitting already exists for this booking
    Boolean existsByBookingId(UUID bookingId);
}