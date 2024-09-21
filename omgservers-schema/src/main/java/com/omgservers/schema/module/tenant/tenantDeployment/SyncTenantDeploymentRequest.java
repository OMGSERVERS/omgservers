package com.omgservers.schema.module.tenant.tenantDeployment;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantDeploymentRequest implements ShardedRequest {

    @NotNull
    TenantDeploymentModel tenantDeployment;

    @Override
    public String getRequestShardKey() {
        return tenantDeployment.getTenantId().toString();
    }
}
