package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionMatchmakerRequest implements ShardedRequest {

    @NotNull
    VersionMatchmakerModel versionMatchmaker;

    @Override
    public String getRequestShardKey() {
        return versionMatchmaker.getTenantId().toString();
    }
}
