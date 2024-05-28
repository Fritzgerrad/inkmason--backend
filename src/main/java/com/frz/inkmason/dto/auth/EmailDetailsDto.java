package com.frz.inkmason.dto.auth;

import com.frz.inkmason.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetailsDto {
    private String recipient;
    private String messageBody;
    private String subject;


}
