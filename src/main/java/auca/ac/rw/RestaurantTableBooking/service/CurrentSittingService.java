package auca.ac.rw.RestaurantTableBooking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.RestaurantTableBooking.modal.Booking;
import auca.ac.rw.RestaurantTableBooking.modal.CurrentSitting;
import auca.ac.rw.RestaurantTableBooking.repository.BookingRepository;
import auca.ac.rw.RestaurantTableBooking.repository.CurrentSittingRepository;

@Service
public class CurrentSittingService {

    @Autowired
    private CurrentSittingRepository currentSittingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Save current sitting
    // Requirement 6: One-to-One
    public String saveSitting(CurrentSitting sitting, String bookingId) {

        // Requirement 7: existsBy check
        // One booking can only have ONE sitting
        if (currentSittingRepository.existsByBookingId(
                UUID.fromString(bookingId))) {
            return "Sitting already exists for this booking";
        }

        // Find the booking
        Booking booking = bookingRepository
            .findById(UUID.fromString(bookingId))
            .orElse(null);

        if (booking == null) return "Booking not found";

        // Link sitting to booking
        sitting.setBooking(booking);

        // Update booking status to CONFIRMED
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

        currentSittingRepository.save(sitting);
        return "Current sitting saved successfully";
    }

    // Get all sittings
    public List<CurrentSitting> getAllSittings() {
        return currentSittingRepository.findAll();
    }

    // Get by id
    public CurrentSitting getSittingById(UUID id) {
        return currentSittingRepository.findById(id).orElse(null);
    }

    // PUT - Full update
    public String updateSitting(UUID id, CurrentSitting sitting) {
        CurrentSitting existing = currentSittingRepository
            .findById(id).orElse(null);
        if (existing == null) return "Sitting not found";
        existing.setSeatedAt(sitting.getSeatedAt());
        existing.setExpectedCheckOut(sitting.getExpectedCheckOut());
        existing.setActualCheckOut(sitting.getActualCheckOut());
        existing.setNotes(sitting.getNotes());
        currentSittingRepository.save(existing);
        return "Sitting updated successfully";
    }

    // PATCH - Update notes only
    public String patchSitting(UUID id, String notes) {
        CurrentSitting existing = currentSittingRepository
            .findById(id).orElse(null);
        if (existing == null) return "Sitting not found";
        existing.setNotes(notes);
        currentSittingRepository.save(existing);
        return "Sitting updated successfully";
    }

    // DELETE
    public String deleteSitting(UUID id) {
        if (!currentSittingRepository.existsById(id)) 
            return "Sitting not found";
        currentSittingRepository.deleteById(id);
        return "Sitting deleted successfully";
    }
}