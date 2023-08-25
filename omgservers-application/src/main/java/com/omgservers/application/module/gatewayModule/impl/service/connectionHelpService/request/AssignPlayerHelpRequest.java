package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    Long connectionId;
    AssignedPlayerModel assignedPlayer;
}
