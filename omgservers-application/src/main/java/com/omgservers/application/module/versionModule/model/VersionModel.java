package com.omgservers.application.module.versionModule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionModel {

    static public VersionModel create(final UUID tenant,
                                      final UUID stage) {
        return create(UUID.randomUUID(), tenant, stage, VersionStageConfigModel.create());
    }

    static public VersionModel create(final UUID tenant,
                                      final UUID stage,
                                      final VersionStageConfigModel stageConfig) {
        return create(UUID.randomUUID(), tenant, stage, stageConfig);
    }

    static public VersionModel create(final UUID uuid,
                                      final UUID tenant,
                                      final UUID stage,
                                      final VersionStageConfigModel stageConfig) {
        return create(uuid, tenant, stage, stageConfig, VersionSourceCodeModel.create(), VersionBytecodeModel.create());
    }

    static public VersionModel create(final UUID uuid,
                                      final UUID tenant,
                                      final UUID stage,
                                      final VersionStageConfigModel stageConfig,
                                      final VersionSourceCodeModel sourceCode,
                                      final VersionBytecodeModel bytecode) {
        Instant now = Instant.now();

        VersionModel version = new VersionModel();
        version.setCreated(now);
        version.setModified(now);
        version.setUuid(uuid);
        version.setTenant(tenant);
        version.setStage(stage);
        version.setStageConfig(stageConfig);
        version.setSourceCode(sourceCode);
        version.setBytecode(bytecode);
        version.setStatus(VersionStatusEnum.NEW);
        version.setErrors(null);
        return version;
    }

    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    UUID tenant;
    UUID stage;
    @ToString.Exclude
    VersionStageConfigModel stageConfig;
    @ToString.Exclude
    VersionSourceCodeModel sourceCode;
    @ToString.Exclude
    VersionBytecodeModel bytecode;
    VersionStatusEnum status;
    @ToString.Exclude
    String errors;
}
