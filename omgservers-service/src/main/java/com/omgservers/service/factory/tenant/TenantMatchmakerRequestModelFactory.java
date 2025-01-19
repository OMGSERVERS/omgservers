package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantMatchmakerRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantMatchmakerRequestModel create(final Long tenantId,
                                               final Long deploymentId) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, matchmakerId, idempotencyKey);
    }

    public TenantMatchmakerRequestModel create(final Long tenantId,
                                               final Long deploymentId,
                                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, matchmakerId, idempotencyKey);
    }

    public TenantMatchmakerRequestModel create(final Long id,
                                               final Long tenantId,
                                               final Long deploymentId,
                                               final Long matchmakerId,
                                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantMatchmakerRequest = new TenantMatchmakerRequestModel();
        tenantMatchmakerRequest.setId(id);
        tenantMatchmakerRequest.setTenantId(tenantId);
        tenantMatchmakerRequest.setDeploymentId(deploymentId);
        tenantMatchmakerRequest.setCreated(now);
        tenantMatchmakerRequest.setModified(now);
        tenantMatchmakerRequest.setIdempotencyKey(idempotencyKey);
        tenantMatchmakerRequest.setMatchmakerId(matchmakerId);
        tenantMatchmakerRequest.setDeleted(false);
        return tenantMatchmakerRequest;
    }
}
