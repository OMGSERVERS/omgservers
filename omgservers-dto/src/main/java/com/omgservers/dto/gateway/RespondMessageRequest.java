package com.omgservers.dto.gateway;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondMessageRequest {

    public static void validate(RespondMessageRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
    }

    URI server;
    Long connectionId;
    MessageModel message;
}
