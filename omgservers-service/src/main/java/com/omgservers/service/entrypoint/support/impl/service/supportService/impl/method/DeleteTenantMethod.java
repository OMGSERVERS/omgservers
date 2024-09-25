package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<DeleteTenantSupportResponse> execute(DeleteTenantSupportRequest request);

}
