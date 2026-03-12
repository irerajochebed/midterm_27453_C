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

    public String saveUser(User user, String locationId) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "User with this email already exists";
        }
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

    public List<User> getSortedUsers(String sortBy, String direction) {
        Sort sort;
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        return userRepository.findAll(sort);
    }

    public Page<User> getPaginatedUsers(
            int page, int size,
            String sortBy, String direction) {
        Sort sort;
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        return userRepository.findAll(PageRequest.of(page, size, sort));
    }

    public List<User> getUsersByVillage(String code, String name) {
        if (code != null) {
            return userRepository.findByLocation_Code(code);
        } else {
            return userRepository.findByLocation_Name(name);
        }
    }

    public List<User> getUsersByCell(String code, String name) {
        if (code != null) {
            return userRepository.findByLocation_Parent_Code(code);
        } else {
            return userRepository.findByLocation_Parent_Name(name);
        }
    }

    public List<User> getUsersBySector(String code, String name) {
        if (code != null) {
            return userRepository
                .findByLocation_Parent_Parent_Code(code);
        } else {
            return userRepository
                .findByLocation_Parent_Parent_Name(name);
        }
    }

    public List<User> getUsersByDistrict(String code, String name) {
        if (code != null) {
            return userRepository
                .findByLocation_Parent_Parent_Parent_Code(code);
        } else {
            return userRepository
                .findByLocation_Parent_Parent_Parent_Name(name);
        }
    }

    public List<User> getUsersByProvince(
            String provinceCode, String provinceName) {
        return userRepository
            .findByLocation_Parent_Parent_Parent_Parent_CodeOrLocation_Parent_Parent_Parent_Parent_Name(
                provinceCode,
                provinceName
            );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public String updateUser(UUID id, User user) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) {
            return "User not found";
        }
        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setPhoneNumber(user.getPhoneNumber());
        existing.setRole(user.getRole());
        userRepository.save(existing);
        return "User updated successfully";
    }

    public String patchUser(UUID id, String phoneNumber) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) {
            return "User not found";
        }
        existing.setPhoneNumber(phoneNumber);
        userRepository.save(existing);
        return "User updated successfully";
    }

    public String deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            return "User not found";
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}