package com.omgservers.schema.module.tenant.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantImageRequest implements ShardedRequest {

    @NotNull
    TenantImageModel tenantImage;

    @Override
    public String getRequestShardKey() {
        return tenantImage.getTenantId().toString();
    }
}
