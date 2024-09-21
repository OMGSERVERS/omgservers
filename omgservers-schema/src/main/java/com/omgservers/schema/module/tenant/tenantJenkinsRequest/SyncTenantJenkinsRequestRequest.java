package com.omgservers.schema.module.tenant.tenantJenkinsRequest;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantJenkinsRequestRequest implements ShardedRequest {

    @NotNull
    TenantJenkinsRequestModel tenantJenkinsRequest;

    @Override
    public String getRequestShardKey() {
        return tenantJenkinsRequest.getTenantId().toString();
    }
}
