package com.omgservers.schema.module.tenant.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantBuildRequestRequest implements ShardedRequest {

    @NotNull
    TenantBuildRequestModel tenantBuildRequest;

    @Override
    public String getRequestShardKey() {
        return tenantBuildRequest.getTenantId().toString();
    }
}
