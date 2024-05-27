package com.frz.inkmason.model.response;

import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface Response {
    StatusCode getStatusCode();
    void setStatusCode(StatusCode statusCode);
    String getStatusMessage();
    void setStatusMessage(String statusMessage);
}
