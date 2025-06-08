package com.omgservers.schema.shard.tenant.tenantStageState;

import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageChangeOfStateDto;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTenantStageStateRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @NotNull
    TenantStageChangeOfStateDto tenantStageChangeOfState;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}