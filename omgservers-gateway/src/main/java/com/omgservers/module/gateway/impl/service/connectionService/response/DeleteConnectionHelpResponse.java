package com.omgservers.module.gateway.impl.service.connectionService.response;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteConnectionHelpResponse {

    Long connectionId;
    AssignedPlayerModel assignedPlayer;

    public Optional<Long> getConnectionId() {
        return Optional.ofNullable(connectionId);
    }

    public Optional<AssignedPlayerModel> getAssignedPlayer() {
        return Optional.ofNullable(assignedPlayer);
    }
}
