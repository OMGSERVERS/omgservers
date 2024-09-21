package com.omgservers.schema.module.tenant.tenantImageRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantImageRefRequest implements ShardedRequest {

    @NotNull
    TenantImageRefModel tenantImageRef;

    @Override
    public String getRequestShardKey() {
        return tenantImageRef.getTenantId().toString();
    }
}
