package com.omgservers.dto.tenantModule;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectPermissionShardRequest implements ShardRequest {

    static public void validate(SyncProjectPermissionShardRequest request) {
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
