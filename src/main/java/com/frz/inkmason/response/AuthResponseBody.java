package com.frz.inkmason.response;

import com.frz.inkmason.enums.Role;
import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseBody{
    private String token;
    private String firstname;
    private Role role;
    private boolean isVerified;
    private Long userId;

}
