package com.omgservers.model.tenantPermission;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantPermissionModel {

    public static void validate(TenantPermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Instant created;

    @NotNull
    Long userId;

    @NotNull
    TenantPermissionEnum permission;
}
