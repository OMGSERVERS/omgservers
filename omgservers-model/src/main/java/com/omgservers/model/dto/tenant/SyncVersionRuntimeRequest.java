package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionRuntimeRequest implements ShardedRequest {

    @NotNull
    VersionRuntimeModel versionRuntime;

    @Override
    public String getRequestShardKey() {
        return versionRuntime.getTenantId().toString();
    }
}
