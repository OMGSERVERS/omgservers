package com.omgservers.model.version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotBlank
    String idempotencyKey;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    VersionConfigModel config;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    String base64Archive;

    @NotNull
    Boolean deleted;
}
