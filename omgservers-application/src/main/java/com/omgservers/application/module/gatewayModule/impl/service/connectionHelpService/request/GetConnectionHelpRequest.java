package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConnectionHelpRequest {

    static public void validate(GetConnectionHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Session session;
}
