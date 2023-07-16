package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondMessageInternalRequest {

    static public void validate(RespondMessageInternalRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
    }

    URI server;
    UUID connection;
    MessageModel message;
}
