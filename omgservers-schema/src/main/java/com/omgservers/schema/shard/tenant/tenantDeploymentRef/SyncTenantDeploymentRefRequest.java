package com.omgservers.schema.shard.tenant.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantDeploymentRefRequest implements ShardRequest {

    @NotNull
    TenantDeploymentRefModel tenantDeploymentRef;

    @Override
    public String getRequestShardKey() {
        return tenantDeploymentRef.getTenantId().toString();
    }
}
