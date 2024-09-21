package com.omgservers.schema.module.tenant.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTenantStagePermissionExistsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @NotNull
    Long userId;

    @NotNull
    TenantStagePermissionEnum tenantStagePermissionQualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
