package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
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
public class TenantStageModelFactory {

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GenerateIdOperation generateIdOperation;

    public TenantStageModel create(final Long tenantId,
                                   final Long projectId) {
        final var id = generateIdOperation.generateId();
        final var secret = generateSecureStringOperation.generateSecureString();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, projectId, secret, idempotencyKey);
    }

    public TenantStageModel create(final Long tenantId,
                                   final Long projectId,
                                   final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var secret = generateSecureStringOperation.generateSecureString();
        return create(id, tenantId, projectId, secret, idempotencyKey);
    }

    public TenantStageModel create(final Long id,
                                   final Long tenantId,
                                   final Long projectId,
                                   final String secret,
                                   final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantStage = new TenantStageModel();
        tenantStage.setId(id);
        tenantStage.setTenantId(tenantId);
        tenantStage.setProjectId(projectId);
        tenantStage.setCreated(now);
        tenantStage.setModified(now);
        tenantStage.setIdempotencyKey(idempotencyKey);
        tenantStage.setSecret(secret);
        tenantStage.setDeleted(false);
        return tenantStage;
    }

}
