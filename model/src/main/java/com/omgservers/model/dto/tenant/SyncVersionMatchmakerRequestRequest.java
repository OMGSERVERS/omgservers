package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
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
