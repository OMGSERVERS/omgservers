package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.getProject;

import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectInternalResponse> getProject(GetProjectShardRequest request);
}
