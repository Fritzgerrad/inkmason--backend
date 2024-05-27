package com.frz.inkmason.controller;

import com.frz.inkmason.dto.auth.CreateUserDto;
import com.frz.inkmason.dto.auth.LoginUserDto;
import com.frz.inkmason.dto.auth.OTPDto;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.response.AuthResponse;
import com.frz.inkmason.model.response.LocalResponse;
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
        if (response.getStatusCode().equals(StatusCode.successful)){
            return ResponseEntity.ok(response);
        }

        if (response.getStatusCode().equals(StatusCode.badRequest)){
            return ResponseEntity.status(400).body(response);
        }

        if (response.getStatusCode().equals(StatusCode.unauthorized)){
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.status(404).body(new LocalResponse(StatusCode.unknownError,"An Unknown Error Occurred"));

    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }

    @PutMapping("/verify")
    public ResponseEntity<Response> verifyAccount(@RequestBody OTPDto otpDto){
        Response response = authenticationService.verifyOTP(otpDto);
        if(response.getStatusCode().equals(StatusCode.badRequest)) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Response> verifyAccount(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "otp") String otp){
        Response response = authenticationService.verifyOTP(new OTPDto(email,otp));
        if(response.getStatusCode().equals(StatusCode.badRequest)) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }








}
