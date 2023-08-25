package com.omgservers.dto.tenantModule;

import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantPermissionInternalRequest implements InternalRequest {

    static public void validate(SyncTenantPermissionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    TenantPermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return permission.getTenantId().toString();
    }
}
