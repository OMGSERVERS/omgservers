package com.omgservers.schema.shard.tenant.tenantImage;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantImagesRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    Long tenantVersionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
