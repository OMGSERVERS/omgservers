package com.omgservers.schema.module.tenant.tenantDeployment;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDeploymentRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantDeploymentId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
