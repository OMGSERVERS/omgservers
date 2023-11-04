package com.omgservers.factory;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeModel create(final Long tenantId,
                               final Long versionId,
                               final RuntimeTypeEnum type,
                               final RuntimeConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, type, config);
    }

    public RuntimeModel create(final Long id,
                               final Long tenantId,
                               final Long versionId,
                               final RuntimeTypeEnum type,
                               final RuntimeConfigModel config) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtime = new RuntimeModel();
        runtime.setId(id);
        runtime.setCreated(now);
        runtime.setModified(now);
        runtime.setTenantId(tenantId);
        runtime.setVersionId(versionId);
        runtime.setType(type);
        runtime.setConfig(config);
        runtime.setDeleted(Boolean.FALSE);
        return runtime;
    }
}
