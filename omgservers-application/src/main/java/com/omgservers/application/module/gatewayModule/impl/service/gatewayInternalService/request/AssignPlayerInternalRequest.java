package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request;

import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPlayerInternalRequest {

    static public void validate(AssignPlayerInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    URI server;
    Long connectionId;
    AssignedPlayerModel assignedPlayer;
}
