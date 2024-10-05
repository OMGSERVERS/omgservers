package com.omgservers.schema.module.tenant.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantFilesArchiveRequest implements ShardedRequest {

    @NotNull
    TenantFilesArchiveModel tenantFilesArchive;

    @Override
    public String getRequestShardKey() {
        return tenantFilesArchive.getTenantId().toString();
    }
}
