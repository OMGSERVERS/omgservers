package com.omgservers.schema.module.tenant.tenantProjectPermission;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantProjectPermissionsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantProjectId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
