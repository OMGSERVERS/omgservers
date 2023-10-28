package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
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
