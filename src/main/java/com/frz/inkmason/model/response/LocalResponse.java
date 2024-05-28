package com.frz.inkmason.model.response;

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
    private StatusCode statusCode;
    private String statusMessage;


}
