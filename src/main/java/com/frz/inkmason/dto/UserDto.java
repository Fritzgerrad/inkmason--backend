package com.frz.inkmason.dto;

import com.frz.inkmason.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String phone;
    private Role role;
    private String adminCreationPass;
    //private boolean allowsNewsLetter;

}
