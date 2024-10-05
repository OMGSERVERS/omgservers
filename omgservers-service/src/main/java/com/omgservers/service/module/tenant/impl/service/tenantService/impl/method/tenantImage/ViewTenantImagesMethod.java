package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantImagesMethod {
    Uni<ViewTenantImagesResponse> execute(ViewTenantImagesRequest request);
}
