package com.omgservers.application.module.userModule.impl.service.userHelpService.request;

import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondClientHelpRequest {

    static public void validateRespondClientServiceRequest(RespondClientHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    UUID client;
    MessageModel message;
}
