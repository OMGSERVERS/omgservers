package com.omgservers.service.factory;

import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantModel create() {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, idempotencyKey);
    }

    public TenantModel create(final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, idempotencyKey);
    }

    public TenantModel create(final Long id,
                              final String idempotencyKey) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenant = new TenantModel();
        tenant.setId(id);
        tenant.setCreated(now);
        tenant.setModified(now);
        tenant.setIdempotencyKey(idempotencyKey);
        tenant.setDeleted(false);
        return tenant;
    }
}
