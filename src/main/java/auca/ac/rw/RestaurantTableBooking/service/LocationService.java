package auca.ac.rw.RestaurantTableBooking.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.RestaurantTableBooking.modal.Location;
import auca.ac.rw.RestaurantTableBooking.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // Requirement 2: Save Location with parent/child logic
    public String saveLocation(Location location, String parentId) {

        // Requirement 7: existsBy check
        // Check if location code already exists
        if (locationRepository.existsByCode(location.getCode())) {
            return "Location with this code already exists";
        }

        // If parentId is provided link it to parent
        if (parentId != null) {
            Location parent = locationRepository
                .findById(UUID.fromString(parentId))
                .orElse(null);

            if (parent == null) {
                return "Parent location not found";
            }
            // Set the parent location
            location.setParent(parent);
        }

        locationRepository.save(location);
        return "Location saved successfully";
    }

    // Get all locations
    public Object getAllLocations() {
        return locationRepository.findAll();
    }

    // Get by id
    public Location getLocationById(UUID id) {
        return locationRepository.findById(id).orElse(null);
    }

    // PUT - Full update
    public String updateLocation(UUID id, Location location) {
        Location existing = locationRepository.findById(id).orElse(null);
        if (existing == null) return "Location not found";
        existing.setCode(location.getCode());
        existing.setName(location.getName());
        existing.setType(location.getType());
        locationRepository.save(existing);
        return "Location updated successfully";
    }

    // PATCH - Update name only
    public String patchLocation(UUID id, String name) {
        Location existing = locationRepository.findById(id).orElse(null);
        if (existing == null) return "Location not found";
        existing.setName(name);
        locationRepository.save(existing);
        return "Location updated successfully";
    }

    // DELETE
    public String deleteLocation(UUID id) {
        if (!locationRepository.existsById(id)) return "Location not found";
        locationRepository.deleteById(id);
        return "Location deleted successfully";
    }
}