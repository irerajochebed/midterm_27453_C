package auca.ac.rw.RestaurantTableBooking.repository;

import auca.ac.rw.RestaurantTableBooking.modal.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    // existsBy → checks if location with this code already exists
    // Requirement 7: existsBy() method
    Boolean existsByCode(String code);

    // find location by code → used for province query
    Location findByCode(String code);

    // find location by name → used for province query
    Location findByName(String name);
}