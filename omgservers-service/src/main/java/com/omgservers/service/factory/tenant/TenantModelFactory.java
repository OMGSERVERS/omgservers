package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenant.TenantConfigDto;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantModel create(final TenantConfigDto tenantConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantConfig, idempotencyKey);
    }

    public TenantModel create(final TenantConfigDto tenantConfig,
                              final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantConfig, idempotencyKey);
    }

    public TenantModel create(final Long id,
                              final TenantConfigDto tenantConfig,
                              final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenant = new TenantModel();
        tenant.setId(id);
        tenant.setIdempotencyKey(idempotencyKey);
        tenant.setCreated(now);
        tenant.setModified(now);
        tenant.setConfig(tenantConfig);
        tenant.setDeleted(false);
        return tenant;
    }
}
