package com.tendo.Sonet.controller;

import com.tendo.Sonet.dto.RegistrationDTO;
import com.tendo.Sonet.model.*;
import com.tendo.Sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class LoginController
{
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody RegistrationDTO registrationDTO)
    {
        try
        {
            AppUser appUser =   this.userService.createNewUser(registrationDTO);
            return new ResponseEntity<>(appUser, HttpStatus.CREATED);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Unable to register user due to " + ex.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<AppUser> getUserAfterSuccessfulLogin(Authentication authentication)
    {
        if (authentication == null)
        {
            throw new UsernameNotFoundException("Provide valid Credentials");
        }

        return this.userService.getUserByUsername(authentication.getName())
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username"));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("Test is successful");
    }
}
