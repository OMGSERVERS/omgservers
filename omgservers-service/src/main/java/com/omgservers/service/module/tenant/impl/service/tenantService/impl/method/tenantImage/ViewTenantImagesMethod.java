package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantImagesMethod {
    Uni<ViewTenantImageResponse> execute(ViewTenantImageRequest request);
}
