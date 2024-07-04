package com.frz.inkmason.controller;

import com.frz.inkmason.dto.auth.LoginUserDto;
import com.frz.inkmason.dto.auth.OTPDto;
import com.frz.inkmason.dto.auth.ResetPasswordDTO;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }

    @PostMapping("/verify")
    public ResponseEntity<Response> verifyAccount(@RequestBody OTPDto otpDto){
        Response response = authenticationService.verifyOTP(otpDto);
        if(response.getStatusCode()== StatusCode.badRequest.getCode()) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Response> verifyAccount(
            @RequestParam(value = "identifier") String identifier,
            @RequestParam(value = "otp") String otp){
        Response response = authenticationService.verifyOTP(new OTPDto(identifier,otp));
        if(response.getStatusCode() == StatusCode.badRequest.getCode()) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/resend-otp")
    public ResponseEntity<Response> resendOTP(
            @RequestParam(value = "identifier") String identifier){
        Response response = authenticationService.resendOTP(identifier);
        if(response.getStatusCode() == StatusCode.badRequest.getCode()) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<Response> forgotPassword(
            @RequestParam(value = "email") String email){
        Response response = authenticationService.sendOTPToCurrentUser("",email);
        if(response.getStatusCode() == StatusCode.badRequest.getCode()) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        Response response = authenticationService.passwordReset(resetPasswordDTO);
        if(response.getStatusCode() == StatusCode.badRequest.getCode()) {
            return ResponseEntity.status(400).body(response);
        }
        else{
            return ResponseEntity.ok(response);
        }
    }
}
