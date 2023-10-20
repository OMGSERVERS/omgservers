package com.omgservers.model.script;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScriptModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Long runtimeId;

    @NotNull
    ScriptTypeEnum type;

    @NotNull
    String state;

    @NotNull
    ScriptConfigModel config;
}
