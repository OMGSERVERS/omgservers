package com.omgservers.model.dto.tenant.versionJenkinsRequest;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
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
