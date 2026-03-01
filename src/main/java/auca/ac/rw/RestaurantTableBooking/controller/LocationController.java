package auca.ac.rw.RestaurantTableBooking.controller;

import auca.ac.rw.RestaurantTableBooking.modal.Location;
import auca.ac.rw.RestaurantTableBooking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // POST → Save location
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveLocation(
            @RequestBody Location location,
            @RequestParam(required = false) String parentId) {
        String result = locationService.saveLocation(location, parentId);
        if (result.equals("Location saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // GET → Get all locations
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllLocations() {
        return new ResponseEntity<>(
            locationService.getAllLocations(),
            HttpStatus.OK
        );
    }

    // GET → Get location by id
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLocationById(@PathVariable UUID id) {
        return new ResponseEntity<>(
            locationService.getLocationById(id),
            HttpStatus.OK
        );
    }

    // PUT → Update full location
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateLocation(
            @PathVariable UUID id,
            @RequestBody Location location) {
        String result = locationService.updateLocation(id, location);
        if (result.equals("Location updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH → Update location name only
    @PatchMapping(
        value = "/patch/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> patchLocation(
            @PathVariable UUID id,
            @RequestParam String name) {
        String result = locationService.patchLocation(id, name);
        if (result.equals("Location updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE → Delete location
    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteLocation(@PathVariable UUID id) {
        String result = locationService.deleteLocation(id);
        if (result.equals("Location deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}