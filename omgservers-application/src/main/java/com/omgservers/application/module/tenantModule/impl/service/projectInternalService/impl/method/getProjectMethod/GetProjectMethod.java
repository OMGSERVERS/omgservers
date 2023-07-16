package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.getProjectMethod;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.GetProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.GetProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);
}
