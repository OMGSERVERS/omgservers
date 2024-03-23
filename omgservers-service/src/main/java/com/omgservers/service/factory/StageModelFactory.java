package com.omgservers.service.factory;

import com.omgservers.model.stage.StageModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageModelFactory {

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GenerateIdOperation generateIdOperation;

    public StageModel create(final Long tenantId,
                             final Long projectId) {
        final var id = generateIdOperation.generateId();
        final var secret = generateSecureStringOperation.generateSecureString();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, projectId, secret, idempotencyKey);
    }

    public StageModel create(final Long tenantId,
                             final Long projectId,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var secret = generateSecureStringOperation.generateSecureString();
        return create(id, tenantId, projectId, secret, idempotencyKey);
    }

    public StageModel create(final Long id,
                             final Long tenantId,
                             final Long projectId,
                             final String secret,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var stage = new StageModel();
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
