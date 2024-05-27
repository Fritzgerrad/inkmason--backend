package com.frz.inkmason.model.response;

import com.frz.inkmason.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String firstname;
    private Role role;
    private boolean isVerified;
}
