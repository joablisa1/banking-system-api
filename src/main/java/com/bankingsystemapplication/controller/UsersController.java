package com.bankingsystemapplication.controller;


import com.bankingsystemapplication.model.Roles;
import com.bankingsystemapplication.model.Users;
import com.bankingsystemapplication.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> listAllUser(){
        return ResponseEntity.ok(usersService.findAllUsers());
    }
    @PostMapping("/user/save")
    public ResponseEntity<Users> saveUser(@RequestBody Users users){
        if(usersService.isUsersPresent(users.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry username exist in the system");
        }
        return ResponseEntity.ok().body(usersService.saveAdmin(users));
    }
    @PutMapping("/user/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> updateUsersByIdResponseEntity(@PathVariable("id") Long id,@RequestBody Users users){
        users.setId(id);
        if(users !=null){
            return  new ResponseEntity<>(usersService.updateUsers(users), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/user/fetch/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> fetchUsersByIdUsersResponseEntity(@PathVariable("id") Long id){
        Users users=usersService.findUserById(id);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

}
