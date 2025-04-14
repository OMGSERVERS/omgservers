package com.omgservers.schema.shard.tenant.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.schema.shard.ShardedRequest;
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
