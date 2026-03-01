package auca.ac.rw.RestaurantTableBooking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.RestaurantTableBooking.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // existsBy → checks if user with this email already exists
    Boolean existsByEmail(String email);

    // Requirement 8: traverse method
    // user → location (sector) → parent (district) → parent (province)
    // match by province code OR province name
    List<User> findByLocation_Parent_Parent_CodeOrLocation_Parent_Parent_Name(
        String provinceCode,
        String provinceName
    );
}