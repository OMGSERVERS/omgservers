package com.omgservers.schema.module.tenant.tenantDeploymentResource;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantDeploymentResourceRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long deploymentId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
