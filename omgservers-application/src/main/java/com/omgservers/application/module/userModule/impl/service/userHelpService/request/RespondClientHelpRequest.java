package com.omgservers.application.module.userModule.impl.service.userHelpService.request;

import com.omgservers.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondClientHelpRequest {

    static public void validateRespondClientServiceRequest(RespondClientHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long clientId;
    MessageModel message;
}
