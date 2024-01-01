package com.tendo.Sonet.controller;

import com.tendo.Sonet.dto.PasswordDTO;
import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.service.SonetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;

@RestController
@RequestMapping("/api/sonet")
public class SonetUserController
{
    @Autowired
    private SonetUserService sonetUserService;

    @PostMapping("/sonet-users")
    public ResponseEntity<SonetUser> updateSonetUser(@ModelAttribute  SonetUser user)
    {
        SonetUser sonetUser = this.sonetUserService.updateSonetUser(user);

        return new ResponseEntity<>(sonetUser, HttpStatus.CREATED);
    }

    @GetMapping("/sonet-users/{id}")
    public ResponseEntity<SonetUser> getSonetUser( @PathVariable(value = "id") Long id)
    {
        SonetUser sonetUser = this.sonetUserService.getSonetUserByID(id);

        return new ResponseEntity<>(sonetUser, HttpStatus.OK);
    }

    @GetMapping("/sonet-users")
    public ResponseEntity<SonetUser> getSonetUser(@RequestParam String username)
    {
        if(username == null || username.isEmpty()) {
            throw new RuntimeException("Please provide a Username value");
        }

        SonetUser sonetUser = this.sonetUserService.getSonetUserByUsername(username);
        return new ResponseEntity<>(sonetUser, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changeUserPassword(@RequestBody PasswordDTO passwordDTO)
    {
        if(passwordDTO.getCurrentPassword() == null || passwordDTO.getCurrentPassword().isEmpty()) {
            throw new RuntimeException("Please provide your Current Password");
        }

        if(passwordDTO.getNewPassword() == null || passwordDTO.getNewPassword().isEmpty()) {
            throw new RuntimeException("Please provide a New Password");
        }
        this.sonetUserService.updatePassword(passwordDTO);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
