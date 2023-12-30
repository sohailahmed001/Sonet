package com.tendo.Sonet.controller;

import com.tendo.Sonet.model.SonetUser;
import com.tendo.Sonet.service.SonetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sonet")
public class SonetUserController
{
    @Autowired
    private SonetUserService sonetUserService;

    @PostMapping("/sonet-users")
    public ResponseEntity<SonetUser> updateSonetUser(@RequestBody SonetUser user)
    {
        SonetUser sonetUser = this.sonetUserService.updateSonetUser(user);

        return new ResponseEntity<>(sonetUser, HttpStatus.CREATED);
    }

    @GetMapping("/sonet-users/{id}")
    public ResponseEntity<SonetUser> updateSonetUser( @PathVariable Long id)
    {
        SonetUser sonetUser = this.sonetUserService.getSonetUserByID(id);

        return new ResponseEntity<>(sonetUser, HttpStatus.FOUND);
    }
}
