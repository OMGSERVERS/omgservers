package com.omgservers.schema.shard.tenant.tenantProject;

import com.omgservers.schema.shard.ShardedRequest;
import com.omgservers.schema.model.project.TenantProjectModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantProjectRequest implements ShardedRequest {

    @NotNull
    TenantProjectModel tenantProject;

    @Override
    public String getRequestShardKey() {
        return tenantProject.getTenantId().toString();
    }
}
