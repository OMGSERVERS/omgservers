package com.omgservers.schema.shard.tenant.tenantStage;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantStagesRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantProjectId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
