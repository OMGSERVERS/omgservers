package com.omgservers.schema.shard.tenant.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantDeploymentResourceRequest implements ShardRequest {

    @NotNull
    TenantDeploymentResourceModel tenantDeploymentResource;

    @Override
    public String getRequestShardKey() {
        return tenantDeploymentResource.getTenantId().toString();
    }
}
