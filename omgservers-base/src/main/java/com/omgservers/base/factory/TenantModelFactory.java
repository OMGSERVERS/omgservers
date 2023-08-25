package com.omgservers.base.factory;

import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.model.tenant.TenantModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantModel create(final TenantConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, config);
    }

    public TenantModel create(final Long id, final TenantConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        var now = Instant.now();

        TenantModel tenant = new TenantModel();
        tenant.setId(generateIdOperation.generateId());
        tenant.setCreated(now);
        tenant.setModified(now);
        tenant.setConfig(config);
        return tenant;
    }
}
