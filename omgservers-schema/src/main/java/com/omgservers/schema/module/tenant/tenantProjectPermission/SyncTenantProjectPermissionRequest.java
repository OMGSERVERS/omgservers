package com.omgservers.schema.module.tenant.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantProjectPermissionRequest implements ShardedRequest {

    @NotNull
    TenantProjectPermissionModel tenantProjectPermission;

    @Override
    public String getRequestShardKey() {
        return tenantProjectPermission.getTenantId().toString();
    }
}
