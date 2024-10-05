package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantFilesArchivesMethod {
    Uni<ViewTenantFilesArchivesResponse> execute(ViewTenantFilesArchivesRequest request);
}
