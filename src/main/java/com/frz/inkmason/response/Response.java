package com.frz.inkmason.response;

import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface Response {
    int getStatusCode();
    void setStatusCode(int statusCode);
    String getStatusMessage();
    void setStatusMessage(String statusMessage);
}
