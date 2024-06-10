package com.frz.inkmason.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    unauthorized(91),
    badRequest(92),
    successful(0),
    unknownError(99);


    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

}
