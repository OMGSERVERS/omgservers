package com.omgservers.schema.module.tenant.versionJenkinsRequest;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionJenkinsRequestRequest implements ShardedRequest {

    @NotNull
    VersionJenkinsRequestModel versionJenkinsRequest;

    @Override
    public String getRequestShardKey() {
        return versionJenkinsRequest.getTenantId().toString();
    }
}
