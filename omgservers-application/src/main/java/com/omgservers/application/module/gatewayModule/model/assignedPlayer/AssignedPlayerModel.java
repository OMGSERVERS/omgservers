package com.omgservers.application.module.gatewayModule.model.assignedPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignedPlayerModel {

    Long tenantId;
    Long stageId;
    Long userId;
    Long playerId;
    Long clientId;
}
