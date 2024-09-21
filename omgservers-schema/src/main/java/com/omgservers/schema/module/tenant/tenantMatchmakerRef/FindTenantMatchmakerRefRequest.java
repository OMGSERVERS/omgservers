package com.omgservers.schema.module.tenant.tenantMatchmakerRef;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantMatchmakerRefRequest implements ShardedRequest {

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
