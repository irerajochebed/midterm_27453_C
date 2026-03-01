package auca.ac.rw.RestaurantTableBooking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import auca.ac.rw.RestaurantTableBooking.modal.Location;
import auca.ac.rw.RestaurantTableBooking.modal.User;
import auca.ac.rw.RestaurantTableBooking.repository.LocationRepository;
import auca.ac.rw.RestaurantTableBooking.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    // Save user with location
    public String saveUser(User user, String locationId) {

        // Requirement 7: existsBy check
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return "User with this email already exists";
        }

        // Link user to location if locationId provided
        if (locationId != null) {
            Location location = locationRepository
                .findById(UUID.fromString(locationId))
                .orElse(null);

            if (location == null) {
                return "Location not found";
            }
            user.setLocation(location);
        }

        userRepository.save(user);
        return "User saved successfully";
    }

    // Requirement 3: Sorting only
    // Sort users by any field ascending or descending
    public List<User> getSortedUsers(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();
        return userRepository.findAll(sort);
    }

    // Requirement 3: Pagination + Sorting combined
    // Get users page by page
    public Page<User> getPaginatedUsers(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();
        return userRepository.findAll(PageRequest.of(page, size, sort));
    }

    // Requirement 8: Get users by province
    // using traversal method
    public List<User> getUsersByProvince(String provinceCode, String provinceName) {
        return userRepository
            .findByLocation_Parent_Parent_CodeOrLocation_Parent_Parent_Name(
                provinceCode,
                provinceName
            );
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Get by id
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    // PUT - Full update
    public String updateUser(UUID id, User user) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return "User not found";
        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setPhoneNumber(user.getPhoneNumber());
        existing.setRole(user.getRole());
        userRepository.save(existing);
        return "User updated successfully";
    }

    // PATCH - Update phone only
    public String patchUser(UUID id, String phoneNumber) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return "User not found";
        existing.setPhoneNumber(phoneNumber);
        userRepository.save(existing);
        return "User updated successfully";
    }

    // DELETE
    public String deleteUser(UUID id) {
        if (!userRepository.existsById(id)) return "User not found";
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}