package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.websocket.Session;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveTextMessageHelpRequest {

    static public void validate(ReceiveTextMessageHelpRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        //TODO: validate fields
    }

    Session session;
    String message;
}
