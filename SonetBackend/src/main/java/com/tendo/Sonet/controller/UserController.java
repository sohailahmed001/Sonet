package com.tendo.Sonet.controller;

import com.tendo.Sonet.dto.CredentialsDTO;
import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.AppUser;
import com.tendo.Sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*
        ;

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

    @PostMapping("/change-credentials")
    public ResponseEntity<Boolean> changeUserCredentials(@RequestBody CredentialsDTO credentialsDTO)
    {
        if(credentialsDTO.getUsername() == null || credentialsDTO.getUsername().isEmpty()) {
            throw new RuntimeException("Please provide a Username");
        }

        if(credentialsDTO.getCurrentPassword() == null || credentialsDTO.getCurrentPassword().isEmpty()) {
            throw new RuntimeException("Please provide your Current Password");
        }

        this.userService.updateCredentials(credentialsDTO);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
