package com.frz.inkmason.model.response;

import com.frz.inkmason.enums.Role;
import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse implements Response{
    private String token;
    private String firstname;
    private Role role;
    private boolean isVerified;
    private StatusCode statusCode;
    private String statusMessage;
    private Long userId;

}
