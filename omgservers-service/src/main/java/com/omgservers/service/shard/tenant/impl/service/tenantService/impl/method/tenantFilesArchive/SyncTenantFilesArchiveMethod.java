package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantFilesArchiveMethod {
    Uni<SyncTenantFilesArchiveResponse> execute(SyncTenantFilesArchiveRequest request);
}
