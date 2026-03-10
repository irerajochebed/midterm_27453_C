package auca.ac.rw.RestaurantTableBooking.repository;

import auca.ac.rw.RestaurantTableBooking.modal.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    
    Boolean existsByCode(String code);

    Location findByCode(String code);

    Location findByName(String name);
}