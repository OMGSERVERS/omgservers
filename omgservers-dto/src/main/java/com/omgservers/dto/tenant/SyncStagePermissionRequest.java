package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.stagePermission.StagePermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStagePermissionRequest implements ShardedRequest {

    public static void validate(SyncStagePermissionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    StagePermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return permission.getTenantId().toString();
    }
}
