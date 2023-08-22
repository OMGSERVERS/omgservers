package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request;

import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionModel;
import com.omgservers.application.InternalRequest;
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
