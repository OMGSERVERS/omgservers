package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantImageRefMethod {
    Uni<FindTenantImageRefResponse> execute(FindTenantImageRefRequest request);
}
