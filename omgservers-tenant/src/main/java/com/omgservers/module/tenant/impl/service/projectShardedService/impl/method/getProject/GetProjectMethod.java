package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.getProject;

import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectInternalResponse> getProject(GetProjectShardedRequest request);
}
