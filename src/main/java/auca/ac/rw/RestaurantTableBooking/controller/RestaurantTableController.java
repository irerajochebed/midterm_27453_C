package auca.ac.rw.RestaurantTableBooking.controller;

import auca.ac.rw.RestaurantTableBooking.modal.RestaurantTable;
import auca.ac.rw.RestaurantTableBooking.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/tables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    // POST → Save table
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveTable(
            @RequestBody RestaurantTable table) {
        String result = restaurantTableService.saveTable(table);
        if (result.equals("Table saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // POST → Assign staff to table (Many-to-Many)
    @PostMapping(
        value = "/assign-staff",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> assignStaff(
            @RequestParam String tableId,
            @RequestParam String staffId) {
        String result = restaurantTableService
            .assignStaffToTable(tableId, staffId);
        if (result.equals("Staff assigned to table successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // GET → Get all tables
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<RestaurantTable>> getAllTables() {
        return new ResponseEntity<>(
            restaurantTableService.getAllTables(),
            HttpStatus.OK
        );
    }

    // GET → Get table by id
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getTableById(@PathVariable UUID id) {
        return new ResponseEntity<>(
            restaurantTableService.getTableById(id),
            HttpStatus.OK
        );
    }

    // GET → Pagination + Sorting
    @GetMapping(
        value = "/paginated",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<RestaurantTable>> getPaginatedTables(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "tableNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return new ResponseEntity<>(
            restaurantTableService.getPaginatedTables(
                page, size, sortBy, direction),
            HttpStatus.OK
        );
    }

    // PUT → Full update table
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateTable(
            @PathVariable UUID id,
            @RequestBody RestaurantTable table) {
        String result = restaurantTableService.updateTable(id, table);
        if (result.equals("Table updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH → Update availability only
    @PatchMapping(
        value = "/patch/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> patchTable(
            @PathVariable UUID id,
            @RequestParam boolean available) {
        String result = restaurantTableService.patchTable(id, available);
        if (result.equals("Table updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE → Delete table
    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteTable(@PathVariable UUID id) {
        String result = restaurantTableService.deleteTable(id);
        if (result.equals("Table deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}