package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandBodyDto;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantStageCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantStageCommandModel create(final Long tenantId,
                                          final Long tenantStageId,
                                          final TenantStageCommandBodyDto body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantStageId, body, idempotencyKey);
    }

    public TenantStageCommandModel create(final Long tenantId,
                                          final Long tenantStageId,
                                          final TenantStageCommandBodyDto body,
                                          final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, tenantStageId, body, idempotencyKey);
    }

    public TenantStageCommandModel create(final Long id,
                                          final Long tenantId,
                                          final Long tenantStageId,
                                          final TenantStageCommandBodyDto body,
                                          final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantStageCommand = new TenantStageCommandModel();
        tenantStageCommand.setId(id);
        tenantStageCommand.setTenantId(tenantId);
        tenantStageCommand.setStageId(tenantStageId);
        tenantStageCommand.setCreated(now);
        tenantStageCommand.setModified(now);
        tenantStageCommand.setIdempotencyKey(idempotencyKey);
        tenantStageCommand.setQualifier(body.getQualifier());
        tenantStageCommand.setBody(body);
        tenantStageCommand.setDeleted(false);
        return tenantStageCommand;
    }
}
