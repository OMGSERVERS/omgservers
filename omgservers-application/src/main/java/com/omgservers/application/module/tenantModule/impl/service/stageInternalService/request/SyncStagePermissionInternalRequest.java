package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request;

import com.omgservers.application.module.tenantModule.model.stage.StagePermissionModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStagePermissionInternalRequest implements InternalRequest {

    static public void validate(SyncStagePermissionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    StagePermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
