package com.omgservers.schema.shard.tenant.tenantPermission;

import com.omgservers.schema.shard.ShardedRequest;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantPermissionRequest implements ShardedRequest {

    @NotNull
    TenantPermissionModel tenantPermission;

    @Override
    public String getRequestShardKey() {
        return tenantPermission.getTenantId().toString();
    }
}
