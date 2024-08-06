package com.omgservers.schema.module.runtime;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimePermissionRequest implements ShardedRequest {

    @NotNull
    RuntimePermissionModel runtimePermission;

    @Override
    public String getRequestShardKey() {
        return runtimePermission.getRuntimeId().toString();
    }
}
