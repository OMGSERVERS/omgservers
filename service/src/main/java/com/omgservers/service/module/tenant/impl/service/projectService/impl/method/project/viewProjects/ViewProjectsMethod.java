package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.viewProjects;

import com.omgservers.schema.module.tenant.ViewProjectsRequest;
import com.omgservers.schema.module.tenant.ViewProjectsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewProjectsMethod {
    Uni<ViewProjectsResponse> viewProjects(ViewProjectsRequest request);
}
