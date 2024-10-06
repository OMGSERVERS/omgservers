package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantImageMethod {
    Uni<DeleteTenantImageResponse> execute(DeleteTenantImageRequest request);
}