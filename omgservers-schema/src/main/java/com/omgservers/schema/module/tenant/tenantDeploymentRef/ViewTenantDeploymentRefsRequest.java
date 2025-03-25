package com.omgservers.schema.module.tenant.tenantDeploymentRef;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantDeploymentRefsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
