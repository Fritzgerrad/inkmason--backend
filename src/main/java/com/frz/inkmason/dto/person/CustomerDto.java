package com.frz.inkmason.dto.person;

import com.frz.inkmason.dto.auth.CreateUserDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends CreateUserDto {
    private boolean allowsNewsLetter;

}
