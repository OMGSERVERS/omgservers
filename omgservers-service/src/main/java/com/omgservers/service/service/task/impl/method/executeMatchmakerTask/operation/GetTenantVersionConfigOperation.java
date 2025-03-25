package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionConfigOperation {
    Uni<TenantVersionConfigDto> execute(Long matchmakerId);
}
