package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantFilesArchiveMethod {
    Uni<FindTenantFilesArchiveResponse> execute(FindTenantFilesArchiveRequest request);
}
