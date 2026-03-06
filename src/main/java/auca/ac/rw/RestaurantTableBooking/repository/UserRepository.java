package auca.ac.rw.RestaurantTableBooking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.RestaurantTableBooking.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

    // ─────────────────────────────────────────
    // User linked to VILLAGE level
    // village → cell → sector → district → province
    // ─────────────────────────────────────────

    // Find by VILLAGE (direct location)
    List<User> findByLocation_Name(String villageName);
    List<User> findByLocation_Code(String villageCode);

    // Find by CELL (1 parent up)
    List<User> findByLocation_Parent_Name(String cellName);
    List<User> findByLocation_Parent_Code(String cellCode);

    // Find by SECTOR (2 parents up)
    List<User> findByLocation_Parent_Parent_Name(String sectorName);
    List<User> findByLocation_Parent_Parent_Code(String sectorCode);

    // Find by DISTRICT (3 parents up)
    List<User> findByLocation_Parent_Parent_Parent_Name(String districtName);
    List<User> findByLocation_Parent_Parent_Parent_Code(String districtCode);

    // Find by PROVINCE (4 parents up)
    // Requirement 8: by province code OR name
    List<User> findByLocation_Parent_Parent_Parent_Parent_CodeOrLocation_Parent_Parent_Parent_Parent_Name(
        String provinceCode,String provinceName);
}