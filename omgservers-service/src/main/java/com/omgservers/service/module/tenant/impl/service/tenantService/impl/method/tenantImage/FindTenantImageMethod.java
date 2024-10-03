package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantImageMethod {
    Uni<FindTenantImageResponse> execute(FindTenantImageRequest request);
}
