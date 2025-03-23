package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantLobbyResourceModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantLobbyResourceModel create(final Long tenantId,
                                           final Long deploymentId) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, lobbyId, idempotencyKey);
    }

    public TenantLobbyResourceModel create(final Long tenantId,
                                           final Long deploymentId,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, lobbyId, idempotencyKey);
    }

    public TenantLobbyResourceModel create(final Long id,
                                           final Long tenantId,
                                           final Long deploymentId,
                                           final Long lobbyId,
                                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantLobbyResource = new TenantLobbyResourceModel();
        tenantLobbyResource.setId(id);
        tenantLobbyResource.setTenantId(tenantId);
        tenantLobbyResource.setDeploymentId(deploymentId);
        tenantLobbyResource.setCreated(now);
        tenantLobbyResource.setModified(now);
        tenantLobbyResource.setIdempotencyKey(idempotencyKey);
        tenantLobbyResource.setLobbyId(lobbyId);
        tenantLobbyResource.setDeleted(false);
        return tenantLobbyResource;
    }
}
