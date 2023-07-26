package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request;

import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
