package com.frz.inkmason.dto.auth;

import com.frz.inkmason.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Date dateOfBirth;
    private String phone;
    private Role role;
    private String adminCreationPass;

}
