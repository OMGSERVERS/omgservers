package com.omgservers.schema.module.tenant.tenantMatchmakerRequest;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantMatchmakerRequestRequest implements ShardedRequest {

    @NotNull
    TenantMatchmakerRequestModel tenantMatchmakerRequest;

    @Override
    public String getRequestShardKey() {
        return tenantMatchmakerRequest.getTenantId().toString();
    }
}
