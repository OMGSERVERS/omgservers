package com.omgservers.module.gateway.impl.service.websocketService.request;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanUpHelpRequest {

    static public void validate(CleanUpHelpRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        //TODO: validate fields
    }

    Session session;
}
