package com.service.property.authservice.controller;

import com.service.property.authservice.model.User;
import com.service.property.authservice.service.UserService;
import com.service.property.authservice.utils.ResponseWrapper;
import com.service.property.authservice.utils.UnSuccessfulWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping //works
    public ResponseEntity<ResponseWrapper<List<User>>> getUsers() {

        List<User> users = userService.getUsers();

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All users retrieved successfully", users.size(), users));
    }

    @PostMapping("/create") //works
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);

        if (savedUser == null) {
            return ResponseEntity.ok(new UnSuccessfulWrapper("failure",
                    "Failed to create user"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "User " + savedUser.getUsername() + " created successful", 1, savedUser));

    }

    @PutMapping("/{userId}") //works
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);

        if (updatedUser == null) {
            return ResponseEntity.ok(new UnSuccessfulWrapper("failure",
                    "Failed to create user"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "User " + updatedUser.getUsername() + " updated successful", 1, updatedUser));

    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validateUser(@RequestBody User user) {
        User validateUser = userService.validateUser(user);

        if (validateUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UnSuccessfulWrapper("failure",
                    "Failed to validate user"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "User " + validateUser.getUsername() + " validated successful", 1, validateUser));

    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseWrapper<User>> getUserByUserName(@PathVariable String name) {
        User userByUserName = userService.getUserByUserName(name);

        if (userByUserName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Retrieved user successful", 1, userByUserName));

    }
}
