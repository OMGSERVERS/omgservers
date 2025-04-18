package com.omgservers.schema.shard.tenant.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantImageRequest implements ShardRequest {

    @NotNull
    TenantImageModel tenantImage;

    @Override
    public String getRequestShardKey() {
        return tenantImage.getTenantId().toString();
    }
}
