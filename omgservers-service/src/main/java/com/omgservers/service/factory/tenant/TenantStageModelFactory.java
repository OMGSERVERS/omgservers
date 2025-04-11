package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.operation.server.GenerateSecureStringOperation;
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
                                   final Long projectId,
                                   final TenantStageConfigDto tenantStageConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, projectId, tenantStageConfig, idempotencyKey);
    }

    public TenantStageModel create(final Long tenantId,
                                   final Long projectId,
                                   final TenantStageConfigDto tenantStageConfig,
                                   final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, projectId, tenantStageConfig, idempotencyKey);
    }

    public TenantStageModel create(final Long id,
                                   final Long tenantId,
                                   final Long projectId,
                                   final TenantStageConfigDto tenantStageConfig,
                                   final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantStage = new TenantStageModel();
        tenantStage.setId(id);
        tenantStage.setIdempotencyKey(idempotencyKey);
        tenantStage.setTenantId(tenantId);
        tenantStage.setProjectId(projectId);
        tenantStage.setCreated(now);
        tenantStage.setModified(now);
        tenantStage.setConfig(tenantStageConfig);
        tenantStage.setDeleted(false);
        return tenantStage;
    }

}
