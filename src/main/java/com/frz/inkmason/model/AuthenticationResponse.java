package com.frz.inkmason.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    String token;
    String statusCode;
    String statusMessage;
}
