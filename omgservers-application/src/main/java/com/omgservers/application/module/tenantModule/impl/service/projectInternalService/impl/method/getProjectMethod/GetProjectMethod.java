package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.getProjectMethod;

import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);
}
