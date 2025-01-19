package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantMatchmakerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantMatchmakerRefModel create(final Long tenantId,
                                           final Long deploymentId,
                                           final Long matchmakerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, matchmakerId, idempotencyKey);
    }

    public TenantMatchmakerRefModel create(final Long tenantId,
                                           final Long deploymentId,
                                           final Long matchmakerId,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, matchmakerId, idempotencyKey);
    }

    public TenantMatchmakerRefModel create(final Long id,
                                           final Long tenantId,
                                           final Long deploymentId,
                                           final Long matchmakerId,
                                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantMatchmakerRef = new TenantMatchmakerRefModel();
        tenantMatchmakerRef.setId(id);
        tenantMatchmakerRef.setTenantId(tenantId);
        tenantMatchmakerRef.setDeploymentId(deploymentId);
        tenantMatchmakerRef.setCreated(now);
        tenantMatchmakerRef.setModified(now);
        tenantMatchmakerRef.setIdempotencyKey(idempotencyKey);
        tenantMatchmakerRef.setMatchmakerId(matchmakerId);
        tenantMatchmakerRef.setDeleted(false);
        return tenantMatchmakerRef;
    }
}
