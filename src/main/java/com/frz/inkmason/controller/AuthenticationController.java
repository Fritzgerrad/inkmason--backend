package com.frz.inkmason.controller;

import com.frz.inkmason.dto.UserDto;
import com.frz.inkmason.model.AuthenticationResponse;
import com.frz.inkmason.model.Role;
import com.frz.inkmason.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Value("${adminCreationPass}")
    private String ADMINCREATIONPASS;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto userDto){
        if(userDto.getRole().equals(Role.admin) && (userDto.getAdminCreationPass() == null || !userDto.getAdminCreationPass().equals(ADMINCREATIONPASS))){
            AuthenticationResponse response = new AuthenticationResponse("","91","You don't have Permission to create an admin");
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.authenticate(userDto));
    }

    @GetMapping("/login")
    public String test(){
        return "Yes it is working";
    }








}
