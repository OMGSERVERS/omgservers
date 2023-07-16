package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request;

import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPlayerHelpRequest {

    static public void validate(AssignPlayerHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID connection;
    AssignedPlayerModel assignedPlayer;
}
