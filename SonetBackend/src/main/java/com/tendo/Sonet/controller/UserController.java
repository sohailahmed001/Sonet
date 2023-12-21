package com.tendo.Sonet.controller;

import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.AppUser;
import com.tendo.Sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable(value = "id") Long userId) throws NotFoundException {
        return this.userService.getUserByIdWithRoles(userId)
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElseThrow(() -> new NotFoundException(AppUser.class));
    }

    @PostMapping("/users")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) {
        try {
            AppUser savedUser = this.userService.addOrUpdateUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
        catch (Exception ex) {
            throw new RuntimeException("Unable to update user due to " + ex.getMessage());
        }
    }
}