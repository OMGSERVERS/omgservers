package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.request;

import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleMessageHelpRequest {

    static public void validate(HandleMessageHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long connectionId;
    MessageModel message;
}
