package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantFilesArchiveMethod {
    Uni<DeleteTenantFilesArchiveResponse> execute(DeleteTenantFilesArchiveRequest request);
}
