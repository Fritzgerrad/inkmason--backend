package com.frz.inkmason.controller;

import com.frz.inkmason.response.Response;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class ResponseMaker {
    public ResponseEntity<Response> getResponse(Response response){
        return switch (response.getStatusCode()) {
            case 0 -> ResponseEntity.ok(response);
            case 92 -> ResponseEntity.status(400).body(response);
            case 91 -> ResponseEntity.status(403).body(response);
            default -> ResponseEntity.status(404).body(response);
        };

    }
}
