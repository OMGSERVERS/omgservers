package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantFilesArchiveMethod {
    Uni<GetTenantFilesArchiveResponse> execute(GetTenantFilesArchiveRequest request);
}
