package com.omgservers.schema.shard.tenant.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTenantProjectPermissionExistsRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantProjectId;

    @NotNull
    Long userId;

    @NotNull
    TenantProjectPermissionQualifierEnum tenantProjectPermissionQualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
