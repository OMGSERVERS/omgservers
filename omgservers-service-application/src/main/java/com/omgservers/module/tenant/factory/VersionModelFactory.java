package com.omgservers.module.tenant.factory;

import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
            final VersionSourceCodeModel sourceCode,
            final VersionBytecodeModel bytecode) {
        final var id = generateIdOperation.generateId();
        final var defaultMatchmakerId = generateIdOperation.generateId();
        final var defaultRuntimeId = generateIdOperation.generateId();
        return create(id,
                tenantId,
                stageId,
                defaultMatchmakerId,
                defaultRuntimeId,
                versionConfig,
                sourceCode,
                bytecode);
    }

    public VersionModel create(final Long id,
                               final Long tenantId,
                               final Long stageId,
                               final Long defaultMatchmakerId,
                               final Long defaultRuntimeId,
                               final VersionConfigModel versionConfig,
                               final VersionSourceCodeModel sourceCode,
                               final VersionBytecodeModel bytecode) {
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
        version.setBytecode(bytecode);
        return version;
    }
}