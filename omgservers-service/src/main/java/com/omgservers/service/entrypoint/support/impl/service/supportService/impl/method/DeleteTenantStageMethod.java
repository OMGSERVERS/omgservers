package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageMethod {
    Uni<DeleteTenantStageSupportResponse> execute(DeleteTenantStageSupportRequest request);
}
