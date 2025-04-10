package com.omgservers.schema.model.tenantVersion;

import jakarta.validation.Valid;
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
public class TenantVersionModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @Valid
    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    TenantVersionConfigDto config;

    @NotNull
    Boolean deleted;
}
