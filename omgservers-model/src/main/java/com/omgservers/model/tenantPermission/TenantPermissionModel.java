package com.omgservers.model.tenantPermission;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantPermissionModel {

    static public void validate(TenantPermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    Long id;
    Long tenantId;
    Instant created;
    Long userId;
    TenantPermissionEnum permission;
}
