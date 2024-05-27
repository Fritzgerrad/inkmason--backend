package com.frz.inkmason.enums;

public enum StatusCode {
    unauthorized(91),
    badRequest(92),
    successful(00),
    unknownError(99);


    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
