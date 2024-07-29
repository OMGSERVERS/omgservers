package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.schema.module.ShardedRequest;
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
