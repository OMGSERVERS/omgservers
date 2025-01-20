package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantMatchmakerRequestMethod {
    Uni<FindTenantMatchmakerRequestResponse> execute(FindTenantMatchmakerRequestRequest request);
}
