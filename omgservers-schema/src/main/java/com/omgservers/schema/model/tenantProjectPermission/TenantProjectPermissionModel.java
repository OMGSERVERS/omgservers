package com.omgservers.schema.model.tenantProjectPermission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectPermissionModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long userId;

    @NotNull
    TenantProjectPermissionEnum permission;

    @NotNull
    Boolean deleted;
}
