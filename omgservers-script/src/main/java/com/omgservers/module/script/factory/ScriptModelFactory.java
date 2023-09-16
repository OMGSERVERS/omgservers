package com.omgservers.module.script.factory;

import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ScriptModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ScriptModel create(final Long tenantId,
                              final Long versionId,
                              final ScriptTypeEnum type,
                              final ScriptConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, type, config);
    }

    public ScriptModel create(final Long id,
                              final Long tenantId,
                              final Long versionId,
                              final ScriptTypeEnum type,
                              final ScriptConfigModel config) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var script = new ScriptModel();
        script.setId(id);
        script.setCreated(now);
        script.setModified(now);
        script.setTenantId(tenantId);
        script.setVersionId(versionId);
        script.setType(type);
        script.setConfig(config);
        return script;
    }
}
