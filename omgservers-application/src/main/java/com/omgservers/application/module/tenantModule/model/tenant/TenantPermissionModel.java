package com.omgservers.application.module.tenantModule.model.tenant;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantPermissionModel {

    static public TenantPermissionModel create(final UUID tenant,
                                               final UUID user,
                                               final TenantPermissionEnum permission) {
        Instant now = Instant.now();

        TenantPermissionModel permissionModel = new TenantPermissionModel();
        permissionModel.setTenant(tenant);
        permissionModel.setCreated(now);
        permissionModel.setUser(user);
        permissionModel.setPermission(permission);
        return permissionModel;
    }

    static public void validateTenantPermission(TenantPermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    UUID tenant;
    @ToString.Exclude
    Instant created;
    UUID user;
    TenantPermissionEnum permission;
}
