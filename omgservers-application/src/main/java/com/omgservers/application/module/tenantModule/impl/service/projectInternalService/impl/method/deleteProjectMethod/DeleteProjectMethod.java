package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.deleteProjectMethod;

import com.omgservers.dto.tenantModule.DeleteProjectInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<Void> deleteProject(DeleteProjectInternalRequest request);
}
