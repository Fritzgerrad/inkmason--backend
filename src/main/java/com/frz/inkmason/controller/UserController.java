package com.frz.inkmason.controller;

import com.frz.inkmason.dto.auth.CreateUserDto;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody CreateUserDto userDto){
        Response response = userService.createUser(userDto);
        if (response.getStatusCode() == StatusCode.successful.getCode()){
            return ResponseEntity.ok(response);
        }

        if (response.getStatusCode() == StatusCode.badRequest.getCode()){
            return ResponseEntity.status(400).body(response);
        }

        if (response.getStatusCode() == StatusCode.unauthorized.getCode()){
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.status(404).body(new LocalResponse(StatusCode.unknownError.getCode(),"An Unknown Error Occurred"));

    }
}
