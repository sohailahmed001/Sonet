package com.tendo.Sonet.controller;

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
    public ResponseEntity<SonetUser> updateSonetUser(@RequestBody  SonetUser user)
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
}
