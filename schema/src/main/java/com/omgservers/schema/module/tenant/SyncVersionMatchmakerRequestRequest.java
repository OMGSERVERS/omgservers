package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionMatchmakerRequestRequest implements ShardedRequest {

    @NotNull
    VersionMatchmakerRequestModel versionMatchmakerRequest;

    @Override
    public String getRequestShardKey() {
        return versionMatchmakerRequest.getTenantId().toString();
    }
}
