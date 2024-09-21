package com.omgservers.schema.module.tenant.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTenantProjectPermissionExistsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantProjectId;

    @NotNull
    Long userId;

    @NotNull
    TenantProjectPermissionEnum tenantProjectPermissionQualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
