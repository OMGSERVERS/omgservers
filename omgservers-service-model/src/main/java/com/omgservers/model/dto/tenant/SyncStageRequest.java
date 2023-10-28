package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.stage.StageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStageRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    StageModel stage;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
