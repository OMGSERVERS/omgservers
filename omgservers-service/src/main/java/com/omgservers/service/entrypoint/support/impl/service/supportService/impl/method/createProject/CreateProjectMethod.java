package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProject;

import com.omgservers.schema.entrypoint.support.CreateProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<CreateProjectSupportResponse> createProject(CreateProjectSupportRequest request);
}
