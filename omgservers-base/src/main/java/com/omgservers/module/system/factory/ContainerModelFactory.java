package com.omgservers.module.system.factory;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerTypeEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ContainerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ContainerModel create(final Long tenantId,
                                 final Long versionId,
                                 final Long runtimeId,
                                 final ContainerTypeEnum type) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, runtimeId, type);
    }

    public ContainerModel create(final Long id,
                                 final Long tenantId,
                                 final Long versionId,
                                 final Long runtimeId,
                                 final ContainerTypeEnum type) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var container = new ContainerModel();
        container.setId(id);
        container.setCreated(now);
        container.setModified(now);
        container.setTenantId(tenantId);
        container.setVersionId(versionId);
        container.setRuntimeId(runtimeId);
        container.setType(type);
        container.setDeleted(false);
        return container;
    }
}
