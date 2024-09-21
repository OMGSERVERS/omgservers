package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantImageRefMethod {
    Uni<DeleteTenantImageRefResponse> execute(DeleteTenantImageRefRequest request);
}
