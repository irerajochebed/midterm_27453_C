package auca.ac.rw.RestaurantTableBooking.controller;

import auca.ac.rw.RestaurantTableBooking.modal.CurrentSitting;
import auca.ac.rw.RestaurantTableBooking.service.CurrentSittingService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sittings")
public class CurrentSittingController {

    @Autowired
    private CurrentSittingService currentSittingService;

    // POST → Save sitting
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveSitting(
            @RequestBody CurrentSitting sitting,
            @RequestParam String bookingId) {
        String result = currentSittingService
            .saveSitting(sitting, bookingId);
        if (result.equals("Current sitting saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // GET → Get all sittings
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CurrentSitting>> getAllSittings() {
        return new ResponseEntity<>(
            currentSittingService.getAllSittings(),
            HttpStatus.OK
        );
    }

    // GET → Get sitting by id
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getSittingById(@PathVariable UUID id) {
        return new ResponseEntity<>(
            currentSittingService.getSittingById(id),
            HttpStatus.OK
        );
    }

    // PUT → Full update sitting
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateSitting(
            @PathVariable UUID id,
            @RequestBody CurrentSitting sitting) {
        String result = currentSittingService.updateSitting(id, sitting);
        if (result.equals("Sitting updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH → Update notes only
    @PatchMapping(
        value = "/patch/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> patchSitting(
            @PathVariable UUID id,
            @RequestParam String notes) {
        String result = currentSittingService.patchSitting(id, notes);
        if (result.equals("Sitting updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE → Delete sitting
    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteSitting(@PathVariable UUID id) {
        String result = currentSittingService.deleteSitting(id);
        if (result.equals("Sitting deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}