package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerModel create(final Long tenantId,
                                  final Long deploymentId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, idempotencyKey);
    }

    public MatchmakerModel create(final Long tenantId,
                                  final Long deploymentId,
                                  final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, idempotencyKey);
    }

    public MatchmakerModel create(final Long id,
                                  final Long tenantId,
                                  final Long deploymentId,
                                  final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmaker = new MatchmakerModel();
        matchmaker.setId(id);
        matchmaker.setIdempotencyKey(idempotencyKey);
        matchmaker.setCreated(now);
        matchmaker.setModified(now);
        matchmaker.setTenantId(tenantId);
        matchmaker.setDeploymentId(deploymentId);
        matchmaker.setDeleted(false);
        return matchmaker;
    }
}
