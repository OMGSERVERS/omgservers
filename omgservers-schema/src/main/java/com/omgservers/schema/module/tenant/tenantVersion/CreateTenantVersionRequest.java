package com.omgservers.schema.module.tenant.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantVersionRequest implements ShardedRequest {

    @NotNull
    TenantVersionModel tenantVersion;

    @Override
    public String getRequestShardKey() {
        return tenantVersion.getId().toString();
    }
}
