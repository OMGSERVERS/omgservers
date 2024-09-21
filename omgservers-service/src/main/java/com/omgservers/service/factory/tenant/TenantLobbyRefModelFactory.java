package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantLobbyRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantLobbyRefModel create(final Long tenantId,
                                      final Long deploymentId,
                                      final Long lobbyId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, lobbyId, idempotencyKey);
    }

    public TenantLobbyRefModel create(final Long tenantId,
                                      final Long deploymentId,
                                      final Long lobbyId,
                                      final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, lobbyId, idempotencyKey);
    }

    public TenantLobbyRefModel create(final Long id,
                                      final Long tenantId,
                                      final Long deploymentId,
                                      final Long lobbyId,
                                      final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantLobbyRef = new TenantLobbyRefModel();
        tenantLobbyRef.setId(id);
        tenantLobbyRef.setTenantId(tenantId);
        tenantLobbyRef.setDeploymentId(deploymentId);
        tenantLobbyRef.setCreated(now);
        tenantLobbyRef.setModified(now);
        tenantLobbyRef.setIdempotencyKey(idempotencyKey);
        tenantLobbyRef.setLobbyId(lobbyId);
        tenantLobbyRef.setDeleted(false);
        return tenantLobbyRef;
    }
}
