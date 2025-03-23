package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantMatchmakerResourceModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantMatchmakerResourceModel create(final Long tenantId,
                                                final Long deploymentId) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, matchmakerId, idempotencyKey);
    }

    public TenantMatchmakerResourceModel create(final Long tenantId,
                                                final Long deploymentId,
                                                final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, matchmakerId, idempotencyKey);
    }

    public TenantMatchmakerResourceModel create(final Long id,
                                                final Long tenantId,
                                                final Long deploymentId,
                                                final Long matchmakerId,
                                                final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantMatchmakerResource = new TenantMatchmakerResourceModel();
        tenantMatchmakerResource.setId(id);
        tenantMatchmakerResource.setTenantId(tenantId);
        tenantMatchmakerResource.setDeploymentId(deploymentId);
        tenantMatchmakerResource.setCreated(now);
        tenantMatchmakerResource.setModified(now);
        tenantMatchmakerResource.setIdempotencyKey(idempotencyKey);
        tenantMatchmakerResource.setMatchmakerId(matchmakerId);
        tenantMatchmakerResource.setDeleted(false);
        return tenantMatchmakerResource;
    }
}
