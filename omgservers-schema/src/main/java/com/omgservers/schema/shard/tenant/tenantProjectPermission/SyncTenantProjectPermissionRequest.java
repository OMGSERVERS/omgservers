package com.omgservers.schema.shard.tenant.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantProjectPermissionRequest implements ShardRequest {

    @NotNull
    TenantProjectPermissionModel tenantProjectPermission;

    @Override
    public String getRequestShardKey() {
        return tenantProjectPermission.getTenantId().toString();
    }
}
