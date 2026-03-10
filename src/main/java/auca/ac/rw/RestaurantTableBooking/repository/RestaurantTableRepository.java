package auca.ac.rw.RestaurantTableBooking.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.RestaurantTableBooking.modal.RestaurantTable;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, UUID> {

    Boolean existsByTableNumber(String tableNumber);

   }