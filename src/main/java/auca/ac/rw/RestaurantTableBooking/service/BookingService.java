package auca.ac.rw.RestaurantTableBooking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import auca.ac.rw.RestaurantTableBooking.modal.Booking;
import auca.ac.rw.RestaurantTableBooking.modal.RestaurantTable;
import auca.ac.rw.RestaurantTableBooking.modal.User;
import auca.ac.rw.RestaurantTableBooking.repository.BookingRepository;
import auca.ac.rw.RestaurantTableBooking.repository.RestaurantTableRepository;
import auca.ac.rw.RestaurantTableBooking.repository.UserRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    // Save booking
    public String saveBooking(Booking booking, String customerId, String tableId) {

        // Find customer
        User customer = userRepository
            .findById(UUID.fromString(customerId))
            .orElse(null);

        // Find table
        RestaurantTable table = restaurantTableRepository
            .findById(UUID.fromString(tableId))
            .orElse(null);

        if (customer == null) return "Customer not found";
        if (table == null) return "Table not found";

        // Check if table is available
        if (!table.isAvailable()) {
            return "Table is not available";
        }

        // Requirement 7: existsBy check
        // Check if booking already exists for this customer and table
        if (bookingRepository.existsByCustomerIdAndTableId(
                UUID.fromString(customerId),
                UUID.fromString(tableId))) {
            return "Booking already exists for this customer and table";
        }

        // Link customer and table to booking
        booking.setCustomer(customer);
        booking.setTable(table);
        booking.setStatus("PENDING");

        // Mark table as unavailable
        table.setAvailable(false);
        restaurantTableRepository.save(table);

        bookingRepository.save(booking);
        return "Booking saved successfully";
    }

    // Requirement 3: Pagination + Sorting
    public Page<Booking> getPaginatedBookings(
            int page, int size,
            String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();
        return bookingRepository
            .findAll(PageRequest.of(page, size, sort));
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get bookings by customer
    public List<Booking> getBookingsByCustomer(String customerId) {
        return bookingRepository
            .findByCustomerId(UUID.fromString(customerId));
    }
    // Get by id
    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // PUT - Full update
    public String updateBooking(UUID id, Booking booking) {
        Booking existing = bookingRepository.findById(id).orElse(null);
        if (existing == null) return "Booking not found";
        existing.setBookingDate(booking.getBookingDate());
        existing.setBookingTime(booking.getBookingTime());
        existing.setNumberOfGuests(booking.getNumberOfGuests());
        existing.setSpecialRequest(booking.getSpecialRequest());
        bookingRepository.save(existing);
        return "Booking updated successfully";
    }

    // PATCH - Update status only
    public String patchBooking(UUID id, String status) {
        Booking existing = bookingRepository.findById(id).orElse(null);
        if (existing == null) return "Booking not found";
        existing.setStatus(status);
        bookingRepository.save(existing);
        return "Booking updated successfully";
    }

    // DELETE
    public String deleteBooking(UUID id) {
        if (!bookingRepository.existsById(id)) return "Booking not found";
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }
}