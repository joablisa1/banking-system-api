package com.bankingsystemapplication.controller;


import com.bankingsystemapplication.model.Roles;
import com.bankingsystemapplication.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.Role;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    UsersService usersService;

    @GetMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Roles>> listAllUser(){
        return ResponseEntity.ok(usersService.findAllRoles());
    }
    @PostMapping("/role/save")
    public ResponseEntity<Roles> saveRole(@RequestBody Roles role){
        if(usersService.isRolesPresent(role.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry role exist in the system");
        }
        return ResponseEntity.ok().body(usersService.saveRole(role));
    }
    @GetMapping("/role/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Roles> findRoleByIDResponseEntity(@PathVariable("id") Long id){
        Roles roles=usersService.findRolesById(id);
        return new ResponseEntity<>(roles,HttpStatus.OK);
    }
    @PutMapping("/role/update/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Roles>updateRolesResponseEntity(@RequestBody Roles roles){
        Roles roles1=usersService.findRolesById(roles.getId());
        roles1.setId(roles.getId());
        roles1.setActivated(false);
        roles1.setName(roles.getName());
        return new ResponseEntity<>(roles1,HttpStatus.ACCEPTED);
    }
}
