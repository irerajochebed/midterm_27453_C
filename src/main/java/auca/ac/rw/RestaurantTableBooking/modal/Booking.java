package auca.ac.rw.RestaurantTableBooking.modal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private int numberOfGuests;
    private String status;
    private String specialRequest;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @JsonIgnore
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private CurrentSitting currentSitting;

    public UUID getId() { 
        return id;
     }
    public void setId(UUID id) { 
        this.id = id; 
    }

    public LocalDate getBookingDate() {
         return bookingDate; 
        }
    public void setBookingDate(LocalDate bookingDate) { 
        this.bookingDate = bookingDate; 
    }

    public LocalTime getBookingTime() { 
        return bookingTime; 
    }
    public void setBookingTime(LocalTime bookingTime) { 
        this.bookingTime = bookingTime; 
    }

    public int getNumberOfGuests() {
         return numberOfGuests; 
        }
    public void setNumberOfGuests(int numberOfGuests) { 
        this.numberOfGuests = numberOfGuests; 
    }

    public String getStatus() {
         return status; 
        }
    public void setStatus(String status) {
         this.status = status;
         }

    public String getSpecialRequest() { 
        return specialRequest; 
    }
    public void setSpecialRequest(String specialRequest) { 
        this.specialRequest = specialRequest; 
    }

    public User getCustomer() {
         return customer;
         }
    public void setCustomer(User customer) { 
        
        this.customer = customer; 
    }

    public RestaurantTable getTable() {
         return table; 
        }
    public void setTable(RestaurantTable table) {
         this.table = table; 
        }

    public CurrentSitting getCurrentSitting() { 
        return currentSitting; 
    }
    public void setCurrentSitting(CurrentSitting currentSitting) { 
        this.currentSitting = currentSitting; 
    }
}