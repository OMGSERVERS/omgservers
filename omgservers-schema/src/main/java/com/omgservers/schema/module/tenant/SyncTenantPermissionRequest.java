package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantPermissionRequest implements ShardedRequest {

    @NotNull
    TenantPermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return permission.getTenantId().toString();
    }
}
