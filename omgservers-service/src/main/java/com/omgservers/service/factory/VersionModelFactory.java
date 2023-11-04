package com.omgservers.service.factory;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionModel create(
            final Long tenantId,
            final Long stageId,
            final VersionConfigModel versionConfig,
            final VersionSourceCodeModel sourceCode) {
        final var id = generateIdOperation.generateId();
        final var defaultMatchmakerId = generateIdOperation.generateId();
        final var defaultRuntimeId = generateIdOperation.generateId();
        return create(id,
                tenantId,
                stageId,
                defaultMatchmakerId,
                defaultRuntimeId,
                versionConfig,
                sourceCode);
    }

    public VersionModel create(final Long id,
                               final Long tenantId,
                               final Long stageId,
                               final Long defaultMatchmakerId,
                               final Long defaultRuntimeId,
                               final VersionConfigModel versionConfig,
                               final VersionSourceCodeModel sourceCode) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var version = new VersionModel();
        version.setId(id);
        version.setTenantId(tenantId);
        version.setStageId(stageId);
        version.setCreated(now);
        version.setModified(now);
        version.setDefaultMatchmakerId(defaultMatchmakerId);
        version.setDefaultRuntimeId(defaultRuntimeId);
        version.setConfig(versionConfig);
        version.setSourceCode(sourceCode);
        return version;
    }
}