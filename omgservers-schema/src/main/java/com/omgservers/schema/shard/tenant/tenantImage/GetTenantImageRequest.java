package com.omgservers.schema.shard.tenant.tenantImage;

import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantImageRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
