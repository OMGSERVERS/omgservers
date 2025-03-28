package com.omgservers.schema.module.tenant.tenantImage;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantImagesRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    Long tenantVersionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
