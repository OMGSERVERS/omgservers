package com.omgservers.schema.module.tenant.tenantMatchmakerResource;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantMatchmakerResourceRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long deploymentId;

    @NotNull
    Long matchmakerId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
