package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantImageRefsMethod {
    Uni<ViewTenantImageRefsResponse> execute(ViewTenantImageRefsRequest request);
}
