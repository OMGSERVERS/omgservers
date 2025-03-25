package com.omgservers.schema.module.tenant.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantDeploymentResourceRequest implements ShardedRequest {

    @NotNull
    TenantDeploymentResourceModel tenantDeploymentResource;

    @Override
    public String getRequestShardKey() {
        return tenantDeploymentResource.getTenantId().toString();
    }
}
