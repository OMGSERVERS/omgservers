package com.omgservers.schema.shard.tenant.tenantDeploymentRef;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantDeploymentRefsRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
