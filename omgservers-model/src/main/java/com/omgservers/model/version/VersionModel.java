package com.omgservers.model.version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionModel {

    Long id;
    Instant created;
    Instant modified;
    Long tenantId;
    Long stageId;
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