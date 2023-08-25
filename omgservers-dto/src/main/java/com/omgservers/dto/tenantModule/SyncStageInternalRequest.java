package com.omgservers.dto.tenantModule;

import com.omgservers.model.stage.StageModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStageInternalRequest implements InternalRequest {

    static public void validate(SyncStageInternalRequest request) {
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
