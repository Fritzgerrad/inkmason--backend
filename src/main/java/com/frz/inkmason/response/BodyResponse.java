package com.frz.inkmason.response;

import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BodyResponse <T> implements  Response{
    private int statusCode;
    private String statusMessage;
    private T data;

}
