package com.omgservers.model.version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionModel {

    Long id;
    Long tenantId;
    Long stageId;
    Instant created;
    Instant modified;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    VersionConfigModel config;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    VersionSourceCodeModel sourceCode;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    VersionBytecodeModel bytecode;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    String errors;
}
