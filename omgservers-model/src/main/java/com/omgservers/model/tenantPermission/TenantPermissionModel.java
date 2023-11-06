package com.omgservers.model.tenantPermission;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantPermissionModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    TenantPermissionEnum permission;

    @NotNull
    Boolean deleted;
}
