package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.stage.StageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStageRequest implements ShardedRequest {

    @NotNull
    StageModel stage;

    @Override
    public String getRequestShardKey() {
        return stage.getTenantId().toString();
    }
}
