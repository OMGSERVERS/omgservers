package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectMethod {
    Uni<DeleteTenantProjectSupportResponse> execute(DeleteTenantProjectSupportRequest request);
}
