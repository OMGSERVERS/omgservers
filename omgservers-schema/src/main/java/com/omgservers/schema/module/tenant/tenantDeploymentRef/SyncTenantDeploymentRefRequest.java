package com.omgservers.schema.module.tenant.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantDeploymentRefRequest implements ShardedRequest {

    @NotNull
    TenantDeploymentRefModel tenantDeploymentRef;

    @Override
    public String getRequestShardKey() {
        return tenantDeploymentRef.getTenantId().toString();
    }
}
