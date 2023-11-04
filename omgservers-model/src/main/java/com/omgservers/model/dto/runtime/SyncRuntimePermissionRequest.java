package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
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
