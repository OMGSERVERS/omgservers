package com.omgservers.schema.module.tenant.tenantImageRef;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantImageRefsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    Long versionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}