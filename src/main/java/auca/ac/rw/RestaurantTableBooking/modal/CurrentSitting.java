package auca.ac.rw.RestaurantTableBooking.modal;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "current_sittings")
public class CurrentSitting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime seatedAt;
    private LocalDateTime expectedCheckOut;
    private LocalDateTime actualCheckOut;
    private String notes;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getSeatedAt() {
        return seatedAt;
    }

    public void setSeatedAt(LocalDateTime seatedAt) {
        this.seatedAt = seatedAt;
    }

    public LocalDateTime getExpectedCheckOut() {
        return expectedCheckOut;
    }

    public void setExpectedCheckOut(LocalDateTime expectedCheckOut) {
        this.expectedCheckOut = expectedCheckOut;
    }

    public LocalDateTime getActualCheckOut() {
        return actualCheckOut;
    }

    public void setActualCheckOut(LocalDateTime actualCheckOut) {
        this.actualCheckOut = actualCheckOut;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
}
