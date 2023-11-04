package com.omgservers.service.factory;

import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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

    public TenantModel create() {
        final var id = generateIdOperation.generateId();
        return create(id);
    }

    public TenantModel create(final Long id) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        TenantModel tenant = new TenantModel();
        tenant.setId(id);
        tenant.setCreated(now);
        tenant.setModified(now);
        tenant.setDeleted(false);
        return tenant;
    }
}
