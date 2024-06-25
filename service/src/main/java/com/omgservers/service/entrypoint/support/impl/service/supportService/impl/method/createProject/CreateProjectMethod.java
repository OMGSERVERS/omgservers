package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProject;

import com.omgservers.model.dto.support.CreateProjectSupportRequest;
import com.omgservers.model.dto.support.CreateProjectSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<CreateProjectSupportResponse> createProject(CreateProjectSupportRequest request);
}
