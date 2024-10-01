package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteTenantStagePermissionsOperation {
    Uni<Void> execute(Long tenantId, Long tenantStageId);
}
