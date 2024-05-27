package com.frz.inkmason.controller;

import com.frz.inkmason.dto.auth.CreateUserDto;
import com.frz.inkmason.dto.auth.LoginUserDto;
import com.frz.inkmason.model.response.AuthResponse;
import com.frz.inkmason.model.response.Response;
import com.frz.inkmason.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody CreateUserDto userDto){
        Response response = authenticationService.register(userDto);
        if (response.getStatusCode() == 00){
            return ResponseEntity.ok(response);
        }

        if (response.getStatusCode() == 91){
            return ResponseEntity.status(400).body(response);
        }

        if (response.getStatusCode() == 92){
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.status(404).body(new Response(new AuthResponse(),99,"An Unknown Error Occurred"));

    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }

    @GetMapping("/login")
    public String test(){
        return "Yes it is working";
    }








}
