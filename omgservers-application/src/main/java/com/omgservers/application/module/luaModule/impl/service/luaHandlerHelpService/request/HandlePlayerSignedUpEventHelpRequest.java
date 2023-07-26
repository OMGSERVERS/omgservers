package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlePlayerSignedUpEventHelpRequest {

    static public void validate(HandlePlayerSignedUpEventHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long stageId;
    Long userId;
    Long playerId;
    Long clientId;
}
