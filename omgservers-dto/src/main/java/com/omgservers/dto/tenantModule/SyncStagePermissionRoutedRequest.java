package com.omgservers.dto.tenantModule;

import com.omgservers.dto.RoutedRequest;
import com.omgservers.model.stagePermission.StagePermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStagePermissionRoutedRequest implements RoutedRequest {

    static public void validate(SyncStagePermissionRoutedRequest request) {
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
