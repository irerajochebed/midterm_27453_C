package auca.ac.rw.RestaurantTableBooking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.RestaurantTableBooking.modal.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // existsBy → checks if booking already exists
    // for this customer and table
    Boolean existsByCustomerIdAndTableId(UUID customerId, UUID tableId);

    // Get all bookings for a specific customer
    List<Booking> findByCustomerId(UUID customerId);

}