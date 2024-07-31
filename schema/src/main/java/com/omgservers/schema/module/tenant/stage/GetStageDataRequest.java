package com.omgservers.schema.module.tenant.stage;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageDataRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
