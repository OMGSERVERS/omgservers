package com.omgservers.module.user.impl.service.userService.request;

import com.omgservers.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondClientRequest {

    public static void validateRespondClientServiceRequest(RespondClientRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long clientId;
    MessageModel message;
}
