package com.omgservers.dto.gatewayModule;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

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
    Long connectionId;
    MessageModel message;
}
