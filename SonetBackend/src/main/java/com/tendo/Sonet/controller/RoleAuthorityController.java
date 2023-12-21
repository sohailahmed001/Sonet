package com.tendo.Sonet.controller;


import com.tendo.Sonet.exception.NotFoundException;
import com.tendo.Sonet.model.*;
import com.tendo.Sonet.service.RoleService;
import com.tendo.Sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RoleAuthorityController
{

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @GetMapping("/authorities")
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        List<Authority> authorities = this.userService.getAllAuthorities();
        return new ResponseEntity<>(authorities, HttpStatus.OK);
    }

    @PostMapping("/authorities")
    public ResponseEntity<Authority> addAuthority(@RequestBody Authority authority)
    {
        try
        {
            Authority   savedAuthority  =   this.userService.saveAuthority(authority);
            return new ResponseEntity<>(savedAuthority, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            throw new RuntimeException("Unable to create authority due to " + ex.getMessage());
        }
    }

    @DeleteMapping("/authorities/{id}")
    public ResponseEntity<Boolean> deleteAuthority(@PathVariable(name = "id") Long authorityId)
    {
        try
        {
            this.userService.deleteAuthority(authorityId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        catch (Exception ex) {
            throw new RuntimeException("Unable to delete authority due to " + ex.getMessage());
        }
    }

    @GetMapping("/roles")
    private ResponseEntity<List<Role>> getRoles()
    {
        List<Role> roles = roleService.getAllRolesWithAuthorities();
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    private ResponseEntity<Role> getRoleByID(@PathVariable(value = "id") Long id) throws NotFoundException
    {
        Role role = roleService.getRoleByIdWithAuthorities(id);

        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> addRole(@RequestBody Role role)
    {
        try
        {
            Role    savedRole   =   roleService.saveRole(role);
            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Unable to create role due to " + ex.getMessage());
        }
    }
}
