package com.frz.inkmason.response;

import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalResponse implements Response{
    private int statusCode;
    private String statusMessage;


}
