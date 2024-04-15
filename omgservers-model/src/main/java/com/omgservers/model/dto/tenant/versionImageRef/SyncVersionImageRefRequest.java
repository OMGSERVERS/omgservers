package com.omgservers.model.dto.tenant.versionImageRef;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionImageRef.VersionImageRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionImageRefRequest implements ShardedRequest {

    @NotNull
    VersionImageRefModel versionImageRef;

    @Override
    public String getRequestShardKey() {
        return versionImageRef.getTenantId().toString();
    }
}
