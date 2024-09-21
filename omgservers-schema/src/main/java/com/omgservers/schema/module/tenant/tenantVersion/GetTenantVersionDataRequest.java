package com.omgservers.schema.module.tenant.tenantVersion;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantVersionDataRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantVersionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
