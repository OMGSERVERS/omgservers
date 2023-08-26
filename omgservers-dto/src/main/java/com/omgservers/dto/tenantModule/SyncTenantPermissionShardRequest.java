package com.omgservers.dto.tenantModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantPermissionShardRequest implements ShardRequest {

    static public void validate(SyncTenantPermissionShardRequest request) {
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
