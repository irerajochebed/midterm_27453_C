package auca.ac.rw.RestaurantTableBooking.controller;

import auca.ac.rw.RestaurantTableBooking.modal.User;
import auca.ac.rw.RestaurantTableBooking.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST → Save user
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveUser(
            @RequestBody User user,
            @RequestParam(required = false) String locationId) {
        String result = userService.saveUser(user, locationId);
        if (result.equals("User saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // GET → Get all users
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(
            userService.getAllUsers(),
            HttpStatus.OK
        );
    }

    // GET → Get user by id
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return new ResponseEntity<>(
            userService.getUserById(id),
            HttpStatus.OK
        );
    }

    // GET → Sorting
    @GetMapping(
        value = "/sorted",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<User>> getSortedUsers(
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return new ResponseEntity<>(
            userService.getSortedUsers(sortBy, direction),
            HttpStatus.OK
        );
    }

    // GET → Pagination + Sorting
    @GetMapping(
        value = "/paginated",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<User>> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return new ResponseEntity<>(
            userService.getPaginatedUsers(page, size, sortBy, direction),
            HttpStatus.OK
        );
    }

    // GET → Users by province
    @GetMapping(
        value = "/by-province",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<User>> getUsersByProvince(
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String provinceName) {
        return new ResponseEntity<>(
            userService.getUsersByProvince(provinceCode, provinceName),
            HttpStatus.OK
        );
    }

    // PUT → Full update user
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUser(
            @PathVariable UUID id,
            @RequestBody User user) {
        String result = userService.updateUser(id, user);
        if (result.equals("User updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH → Update phone number only
    @PatchMapping(
        value = "/patch/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> patchUser(
            @PathVariable UUID id,
            @RequestParam String phoneNumber) {
        String result = userService.patchUser(id, phoneNumber);
        if (result.equals("User updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE → Delete user
    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        String result = userService.deleteUser(id);
        if (result.equals("User deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}