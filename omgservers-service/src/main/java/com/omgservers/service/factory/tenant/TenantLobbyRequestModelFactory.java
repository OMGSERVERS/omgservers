package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantLobbyRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantLobbyRequestModel create(final Long tenantId,
                                          final Long deploymentId) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, lobbyId, idempotencyKey);
    }

    public TenantLobbyRequestModel create(final Long tenantId,
                                          final Long deploymentId,
                                          final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, lobbyId, idempotencyKey);
    }

    public TenantLobbyRequestModel create(final Long id,
                                          final Long tenantId,
                                          final Long deploymentId,
                                          final Long lobbyId,
                                          final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantLobbyRequest = new TenantLobbyRequestModel();
        tenantLobbyRequest.setId(id);
        tenantLobbyRequest.setTenantId(tenantId);
        tenantLobbyRequest.setDeploymentId(deploymentId);
        tenantLobbyRequest.setCreated(now);
        tenantLobbyRequest.setModified(now);
        tenantLobbyRequest.setIdempotencyKey(idempotencyKey);
        tenantLobbyRequest.setLobbyId(lobbyId);
        tenantLobbyRequest.setDeleted(false);
        return tenantLobbyRequest;
    }
}
