package com.omgservers.service.module.gateway.impl.service.connectionService.response;

import com.omgservers.model.assignedClient.AssignedClientModel;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteConnectionResponse {

    Long connectionId;
    AssignedClientModel assignedClient;
    AssignedRuntimeModel assignedRuntime;

    public Optional<Long> getConnectionId() {
        return Optional.ofNullable(connectionId);
    }

    public Optional<AssignedClientModel> getAssignedClient() {
        return Optional.ofNullable(assignedClient);
    }

    public Optional<AssignedRuntimeModel> getAssignedRuntime() {
        return Optional.ofNullable(assignedRuntime);
    }
}
