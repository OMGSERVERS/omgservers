package com.omgservers.dto.tenantModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.stage.StageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStageShardRequest implements ShardRequest {

    static public void validate(SyncStageShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    StageModel stage;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
