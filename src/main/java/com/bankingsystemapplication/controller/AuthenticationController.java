package com.bankingsystemapplication.controller;

import com.bankingsystemapplication.form.JwtResponse;
import com.bankingsystemapplication.form.LogoinRequest;
import com.bankingsystemapplication.model.Roles;
import com.bankingsystemapplication.model.Users;
import com.bankingsystemapplication.security.JwtUtils;
import com.bankingsystemapplication.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired UsersService usersService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    public AuthenticationController(UsersService usersService) {
        this.usersService = usersService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogoinRequest logoinRequest){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logoinRequest.getEmail(), logoinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtUtils.generateJwtToken(authentication);
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        List<String> roles =userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }
}
