package auca.ac.rw.RestaurantTableBooking.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tableNumber;
    private int capacity;
    private String locationInRestaurant;
    private boolean available;
    private String tableType;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedTables")
    private List<User> staffMembers = new ArrayList<>();

    public UUID getId() {
         return id; 
        
    }
    public void setId(UUID id) {
         this.id = id; 
        
    }

    public String getTableNumber() {
         return tableNumber;
        
     }
    public void setTableNumber(String tableNumber) { 
        this.tableNumber = tableNumber; 
    }

    public int getCapacity() {
         return capacity;
        
     }
    public void setCapacity(int capacity) {
         this.capacity = capacity; 
        
    }

    public String getLocationInRestaurant() { 
        return locationInRestaurant;
        
     }
    public void setLocationInRestaurant(String locationInRestaurant) { 
        this.locationInRestaurant = locationInRestaurant; 
    }

    public boolean isAvailable() {
         return available; 
        
    }
    public void setAvailable(boolean available) {
         this.available = available; 
        
    }

    public String getTableType() {
         return tableType; 
        
    }
    public void setTableType(String tableType) {
         this.tableType = tableType; 
        
    }

    public List<User> getStaffMembers() {
         return staffMembers; 
        
    }
    public void setStaffMembers(List<User> staffMembers) { 
        this.staffMembers = staffMembers; 
    }
}