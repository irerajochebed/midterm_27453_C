package auca.ac.rw.RestaurantTableBooking.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany
    @JoinTable(
        name = "table_service",
        joinColumns = @JoinColumn(
            name = "staff_id",
            referencedColumnName = "id" ),
        inverseJoinColumns = @JoinColumn(
            name = "table_id",
            referencedColumnName = "id"
        )
    )
    private List<RestaurantTable> assignedTables = new ArrayList<>();

    public UUID getId() { 
        return id; 
        
    }
    public void setId(UUID id) {
         this.id = id; 
        
    }

    public String getFullName() { 
        return fullName; 
    }
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) {
         this.email = email; 
        }

    public String getPassword() { 
        return password;
     }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getPhoneNumber() {
         return phoneNumber; 
        }
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }

    public ERole getRole() {
         return role; 
        }
    public void setRole(ERole role) {
         this.role = role;
         }

    public Location getLocation() {
         return location; 
        }
    public void setLocation(Location location) { 
        this.location = location; 
    }

    public List<RestaurantTable> getAssignedTables() { 
        return assignedTables; 
    }
    public void setAssignedTables(List<RestaurantTable> assignedTables) { 
        this.assignedTables = assignedTables; 
    }
}