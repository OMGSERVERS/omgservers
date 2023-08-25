package com.omgservers.dto.tenantModule;

import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
