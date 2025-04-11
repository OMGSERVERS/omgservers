package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.project.TenantProjectConfigDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantProjectModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantProjectModel create(final Long tenantId,
                                     final TenantProjectConfigDto tenantProjectConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantProjectConfig, idempotencyKey);
    }

    public TenantProjectModel create(final Long tenantId,
                                     final TenantProjectConfigDto tenantProjectConfig,
                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, tenantProjectConfig, idempotencyKey);
    }

    public TenantProjectModel create(final Long id,
                                     final Long tenantId,
                                     final TenantProjectConfigDto tenantProjectConfig,
                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantProject = new TenantProjectModel();
        tenantProject.setId(id);
        tenantProject.setIdempotencyKey(idempotencyKey);
        tenantProject.setTenantId(tenantId);
        tenantProject.setCreated(now);
        tenantProject.setModified(now);
        tenantProject.setConfig(tenantProjectConfig);
        tenantProject.setDeleted(false);
        return tenantProject;
    }
}
