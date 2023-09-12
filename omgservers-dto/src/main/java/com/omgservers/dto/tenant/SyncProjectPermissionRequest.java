package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectPermissionRequest implements ShardedRequest {

    public static void validate(SyncProjectPermissionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ProjectPermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return permission.getTenantId().toString();
    }
}
