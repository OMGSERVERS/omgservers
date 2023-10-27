package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasTenantPermissionRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long userId;

    @NotNull
    TenantPermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
