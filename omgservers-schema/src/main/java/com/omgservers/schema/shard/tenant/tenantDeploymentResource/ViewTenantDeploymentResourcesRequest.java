package com.omgservers.schema.shard.tenant.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantDeploymentResourcesRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    TenantDeploymentResourceStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
