package com.frz.inkmason.controller;

import com.frz.inkmason.dto.auth.CreateUserDto;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.response.LocalResponse;
import com.frz.inkmason.model.response.Response;
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
}
