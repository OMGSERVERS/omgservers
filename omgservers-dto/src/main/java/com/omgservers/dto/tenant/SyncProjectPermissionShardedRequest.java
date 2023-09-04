package com.omgservers.dto.tenant;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectPermissionShardedRequest implements ShardedRequest {

    public static void validate(SyncProjectPermissionShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    ProjectPermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
