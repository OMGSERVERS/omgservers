package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMatchmakerRequestMethod {
    Uni<DeleteTenantMatchmakerRequestResponse> execute(DeleteTenantMatchmakerRequestRequest request);
}
