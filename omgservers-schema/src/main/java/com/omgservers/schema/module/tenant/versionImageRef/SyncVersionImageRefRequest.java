package com.omgservers.schema.module.tenant.versionImageRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import com.omgservers.schema.module.ShardedRequest;
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
