package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
import com.omgservers.schema.module.ShardedRequest;
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
