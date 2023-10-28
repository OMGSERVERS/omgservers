package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.version.VersionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    VersionModel version;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
