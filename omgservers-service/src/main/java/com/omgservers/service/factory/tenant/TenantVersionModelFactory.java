
package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantVersionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantVersionModel create(
            final Long tenantId,
            final Long tenantProjectId,
            final TenantVersionConfigDto tenantVersionConfigDto) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id,
                tenantId,
                tenantProjectId,
                tenantVersionConfigDto,
                idempotencyKey);
    }

    public TenantVersionModel create(
            final Long tenantId,
            final Long tenantProjectId,
            final TenantVersionConfigDto tenantVersionConfigDto,
            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id,
                tenantId,
                tenantProjectId,
                tenantVersionConfigDto,
                idempotencyKey);
    }

    public TenantVersionModel create(final Long id,
                                     final Long tenantId,
                                     final Long tenantProjectId,
                                     final TenantVersionConfigDto tenantVersionConfigDto,
                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantVersion = new TenantVersionModel();
        tenantVersion.setId(id);
        tenantVersion.setTenantId(tenantId);
        tenantVersion.setProjectId(tenantProjectId);
        tenantVersion.setCreated(now);
        tenantVersion.setModified(now);
        tenantVersion.setIdempotencyKey(idempotencyKey);
        tenantVersion.setConfig(tenantVersionConfigDto);
        tenantVersion.setDeleted(false);
        return tenantVersion;
    }
}
