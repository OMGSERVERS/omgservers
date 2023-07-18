package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response;

import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteConnectionHelpResponse {

    UUID connection;
    AssignedPlayerModel assignedPlayer;

    public Optional<UUID> getConnection() {
        return Optional.ofNullable(connection);
    }

    public Optional<AssignedPlayerModel> getAssignedPlayer() {
        return Optional.ofNullable(assignedPlayer);
    }
}
