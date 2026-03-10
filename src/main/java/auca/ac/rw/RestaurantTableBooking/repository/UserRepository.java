package auca.ac.rw.RestaurantTableBooking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.RestaurantTableBooking.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

   
    List<User> findByLocation_Name(String villageName);
    List<User> findByLocation_Code(String villageCode);

    List<User> findByLocation_Parent_Name(String cellName);
    List<User> findByLocation_Parent_Code(String cellCode);

    List<User> findByLocation_Parent_Parent_Name(String sectorName);
    List<User> findByLocation_Parent_Parent_Code(String sectorCode);


    List<User> findByLocation_Parent_Parent_Parent_Name(String districtName);
    List<User> findByLocation_Parent_Parent_Parent_Code(String districtCode);

    
    List<User> findByLocation_Parent_Parent_Parent_Parent_CodeOrLocation_Parent_Parent_Parent_Parent_Name(
        String provinceCode,String provinceName);
}