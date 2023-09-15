package com.omgservers.model.assignedPlayer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignedPlayerModel {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Long clientId;
}
