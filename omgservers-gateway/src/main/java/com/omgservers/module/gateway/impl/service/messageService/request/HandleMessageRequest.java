package com.omgservers.module.gateway.impl.service.messageService.request;

import com.omgservers.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleMessageRequest {

    public static void validate(HandleMessageRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long connectionId;
    MessageModel message;
}
