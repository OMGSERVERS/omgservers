package com.omgservers.schema.module.tenant.tenantDeploymentResource;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDeploymentResourceRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
