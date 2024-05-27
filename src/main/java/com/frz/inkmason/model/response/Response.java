package com.frz.inkmason.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class Response<T> {
    private T data;
    int statusCode;
    String statusMessage;
}
