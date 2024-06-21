package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.viewProjects;

import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewProjectsMethod {
    Uni<ViewProjectsResponse> viewProjects(ViewProjectsRequest request);
}
