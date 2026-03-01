package auca.ac.rw.RestaurantTableBooking.controller;

import auca.ac.rw.RestaurantTableBooking.modal.Booking;
import auca.ac.rw.RestaurantTableBooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // POST → Save booking
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveBooking(
            @RequestBody Booking booking,
            @RequestParam String customerId,
            @RequestParam String tableId) {
        String result = bookingService
            .saveBooking(booking, customerId, tableId);
        if (result.equals("Booking saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // GET → Get all bookings
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Booking>> getAllBookings() {
        return new ResponseEntity<>(
            bookingService.getAllBookings(),
            HttpStatus.OK
        );
    }

    // GET → Get booking by id
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getBookingById(@PathVariable UUID id) {
        return new ResponseEntity<>(
            bookingService.getBookingById(id),
            HttpStatus.OK
        );
    }

    // GET → Pagination + Sorting
    @GetMapping(
        value = "/paginated",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<Booking>> getPaginatedBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "bookingDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return new ResponseEntity<>(
            bookingService.getPaginatedBookings(
                page, size, sortBy, direction),
            HttpStatus.OK
        );
    }

    // GET → Bookings by customer
    @GetMapping(
        value = "/by-customer",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Booking>> getByCustomer(
            @RequestParam String customerId) {
        return new ResponseEntity<>(
            bookingService.getBookingsByCustomer(customerId),
            HttpStatus.OK
        );
    }

    // PUT → Full update booking
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateBooking(
            @PathVariable UUID id,
            @RequestBody Booking booking) {
        String result = bookingService.updateBooking(id, booking);
        if (result.equals("Booking updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH → Update booking status only
    @PatchMapping(
        value = "/patch/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> patchBooking(
            @PathVariable UUID id,
            @RequestParam String status) {
        String result = bookingService.patchBooking(id, status);
        if (result.equals("Booking updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE → Delete booking
    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteBooking(@PathVariable UUID id) {
        String result = bookingService.deleteBooking(id);
        if (result.equals("Booking deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}