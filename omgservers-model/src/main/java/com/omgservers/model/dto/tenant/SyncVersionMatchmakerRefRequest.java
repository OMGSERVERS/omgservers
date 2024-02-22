package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionMatchmakerRefRequest implements ShardedRequest {

    @NotNull
    VersionMatchmakerRefModel versionMatchmakerRef;

    @Override
    public String getRequestShardKey() {
        return versionMatchmakerRef.getTenantId().toString();
    }
}
