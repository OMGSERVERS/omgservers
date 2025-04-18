package com.omgservers.schema.shard.tenant.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTenantPermissionExistsRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long userId;

    @NotNull
    TenantPermissionQualifierEnum qualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
