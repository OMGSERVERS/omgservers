package com.omgservers.schema.module.tenant.tenantStage;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantStageDataRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}