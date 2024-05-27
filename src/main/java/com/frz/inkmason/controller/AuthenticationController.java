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

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto userDto){
        AuthenticationResponse response = authenticationService.register(userDto);
        if (response.getStatusCode() == 00){
            return ResponseEntity.ok(response);
        }

        if (response.getStatusCode() == 91){
            return ResponseEntity.status(400).body(response);
        }

        if (response.getStatusCode() == 92){
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.status(404).body(new AuthenticationResponse("",99,"An Unknown Error Occurred"));

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
