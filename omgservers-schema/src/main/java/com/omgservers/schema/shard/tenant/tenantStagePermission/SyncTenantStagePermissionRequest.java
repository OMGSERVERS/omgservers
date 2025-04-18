package com.omgservers.schema.shard.tenant.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantStagePermissionRequest implements ShardRequest {

    @NotNull
    TenantStagePermissionModel tenantStagePermission;

    @Override
    public String getRequestShardKey() {
        return tenantStagePermission.getTenantId().toString();
    }
}
