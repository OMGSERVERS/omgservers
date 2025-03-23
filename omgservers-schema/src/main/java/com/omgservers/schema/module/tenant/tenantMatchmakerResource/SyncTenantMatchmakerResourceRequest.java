package com.omgservers.schema.module.tenant.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantMatchmakerResourceRequest implements ShardedRequest {

    @NotNull
    TenantMatchmakerResourceModel tenantMatchmakerResource;

    @Override
    public String getRequestShardKey() {
        return tenantMatchmakerResource.getTenantId().toString();
    }
}
