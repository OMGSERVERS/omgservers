package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectPermissionRequest implements ShardedRequest {

    @NotNull
    ProjectPermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return permission.getTenantId().toString();
    }
}
