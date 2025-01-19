package com.omgservers.service.operation.assignLobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignLobbyOperationImpl implements AssignLobbyOperation {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final LobbyModel lobby,
                                final String idempotencyKey) {
        final var runtimeId = lobby.getRuntimeId();
        return createRuntimeAssignment(runtimeId, clientId, idempotencyKey);
    }

    Uni<Boolean> createRuntimeAssignment(final Long runtimeId,
                                         final Long clientId,
                                         final String idempotencyKey) {
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId,
                clientId,
                idempotencyKey);
        final var request = new SyncRuntimeAssignmentRequest(runtimeAssignment);
        return runtimeModule.getService().executeWithIdempotency(request)
                .map(SyncRuntimeAssignmentResponse::getCreated);
    }
}
