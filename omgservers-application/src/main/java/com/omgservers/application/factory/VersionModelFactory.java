package com.omgservers.application.factory;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.model.version.VersionStatusEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionModel create(
            final Long tenantId,
            final Long stageId,
            final VersionStageConfigModel stageConfig,
            final VersionSourceCodeModel sourceCode,
            final VersionBytecodeModel bytecode) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, stageId, stageConfig, sourceCode, bytecode);
    }

    public VersionModel create(final Long id,
                               final Long tenantId,
                               final Long stageId,
                               final VersionStageConfigModel stageConfig,
                               final VersionSourceCodeModel sourceCode,
                               final VersionBytecodeModel bytecode) {
        Instant now = Instant.now();

        VersionModel version = new VersionModel();
        version.setId(id);
        version.setCreated(now);
        version.setModified(now);
        version.setTenantId(tenantId);
        version.setStageId(stageId);
        version.setStageConfig(stageConfig);
        version.setSourceCode(sourceCode);
        version.setBytecode(bytecode);
        version.setStatus(VersionStatusEnum.NEW);
        version.setErrors(null);
        return version;
    }
}
