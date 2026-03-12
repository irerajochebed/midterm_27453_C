package auca.ac.rw.RestaurantTableBooking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import auca.ac.rw.RestaurantTableBooking.modal.RestaurantTable;
import auca.ac.rw.RestaurantTableBooking.modal.User;
import auca.ac.rw.RestaurantTableBooking.repository.RestaurantTableRepository;
import auca.ac.rw.RestaurantTableBooking.repository.UserRepository;

@Service
public class RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private UserRepository userRepository;

    public String saveTable(RestaurantTable table) {
        if (restaurantTableRepository.existsByTableNumber(
                table.getTableNumber())) {
            return "Table with this number already exists";
        }
        table.setAvailable(true);
        restaurantTableRepository.save(table);
        return "Table saved successfully";
    }

    public String assignStaffToTable(String tableId, String staffId) {
        RestaurantTable table = restaurantTableRepository
            .findById(UUID.fromString(tableId))
            .orElse(null);
        User staff = userRepository
            .findById(UUID.fromString(staffId))
            .orElse(null);
        if (table == null) {
            return "Table not found";
        }
        if (staff == null) {
            return "Staff not found";
        }
        List<RestaurantTable> assignedTables = staff.getAssignedTables();
        if (!assignedTables.contains(table)) {
            assignedTables.add(table);
            staff.setAssignedTables(assignedTables);
            userRepository.save(staff);
        }
        return "Staff assigned to table successfully";
    }

    public Page<RestaurantTable> getPaginatedTables(
            int page, int size,
            String sortBy, String direction) {
        Sort sort;
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        return restaurantTableRepository
            .findAll(PageRequest.of(page, size, sort));
    }

    public List<RestaurantTable> getAllTables() {
        return restaurantTableRepository.findAll();
    }

    public RestaurantTable getTableById(UUID id) {
        return restaurantTableRepository.findById(id).orElse(null);
    }

    public String updateTable(UUID id, RestaurantTable table) {
        RestaurantTable existing = restaurantTableRepository
            .findById(id)
            .orElse(null);
        if (existing == null) {
            return "Table not found";
        }
        existing.setTableNumber(table.getTableNumber());
        existing.setCapacity(table.getCapacity());
        existing.setLocationInRestaurant(table.getLocationInRestaurant());
        existing.setTableType(table.getTableType());
        restaurantTableRepository.save(existing);
        return "Table updated successfully";
    }

    public String patchTable(UUID id, boolean available) {
        RestaurantTable existing = restaurantTableRepository
            .findById(id)
            .orElse(null);
        if (existing == null) {
            return "Table not found";
        }
        existing.setAvailable(available);
        restaurantTableRepository.save(existing);
        return "Table updated successfully";
    }

    public String deleteTable(UUID id) {
        if (!restaurantTableRepository.existsById(id)) {
            return "Table not found";
        }
        restaurantTableRepository.deleteById(id);
        return "Table deleted successfully";
    }
}