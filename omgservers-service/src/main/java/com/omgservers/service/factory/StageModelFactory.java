package com.omgservers.service.factory;

import com.omgservers.model.stage.StageModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public StageModel create(final Long tenantId,
                             final Long projectId) {
        final var id = generateIdOperation.generateId();
        final var secret = String.valueOf(new SecureRandom().nextLong());
        final var idempotencyKey = UUID.randomUUID().toString();
        return create(id, tenantId, projectId, secret, idempotencyKey);
    }

    public StageModel create(final Long tenantId,
                             final Long projectId,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var secret = String.valueOf(new SecureRandom().nextLong());
        return create(id, tenantId, projectId, secret, idempotencyKey);
    }

    public StageModel create(final Long id,
                             final Long tenantId,
                             final Long projectId,
                             final String secret,
                             final String idempotencyKey) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        StageModel stage = new StageModel();
        stage.setId(id);
        stage.setTenantId(tenantId);
        stage.setProjectId(projectId);
        stage.setCreated(now);
        stage.setModified(now);
        stage.setIdempotencyKey(idempotencyKey);
        stage.setSecret(secret);
        stage.setDeleted(false);
        return stage;
    }

}
