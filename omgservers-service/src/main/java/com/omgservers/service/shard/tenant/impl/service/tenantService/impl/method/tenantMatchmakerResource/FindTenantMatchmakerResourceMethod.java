package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.FindTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.FindTenantMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantMatchmakerResourceMethod {
    Uni<FindTenantMatchmakerResourceResponse> execute(FindTenantMatchmakerResourceRequest request);
}
