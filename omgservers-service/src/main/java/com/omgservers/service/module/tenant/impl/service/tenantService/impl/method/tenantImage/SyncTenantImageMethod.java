package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantImageMethod {
    Uni<SyncTenantImageResponse> execute(SyncTenantImageRequest request);
}
