package com.frz.inkmason.dto.person;

import com.frz.inkmason.dto.auth.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends CreateUserDto {
    private boolean allowsNewsLetter;

}
