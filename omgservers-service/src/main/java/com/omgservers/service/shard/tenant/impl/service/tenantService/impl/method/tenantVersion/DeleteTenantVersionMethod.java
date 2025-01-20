package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantVersionMethod {
    Uni<DeleteTenantVersionResponse> execute(DeleteTenantVersionRequest request);
}
