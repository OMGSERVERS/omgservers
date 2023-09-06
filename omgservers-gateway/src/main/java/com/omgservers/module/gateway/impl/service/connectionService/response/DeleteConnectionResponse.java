package com.omgservers.module.gateway.impl.service.connectionService.response;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteConnectionResponse {

    Long connectionId;
    AssignedPlayerModel assignedPlayer;
    AssignedRuntimeModel assignedRuntime;

    public Optional<Long> getConnectionId() {
        return Optional.ofNullable(connectionId);
    }

    public Optional<AssignedPlayerModel> getAssignedPlayer() {
        return Optional.ofNullable(assignedPlayer);
    }

    public Optional<AssignedRuntimeModel> getAssignedRuntime() {
        return Optional.ofNullable(assignedRuntime);
    }
}
