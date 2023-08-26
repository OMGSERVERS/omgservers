package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.deleteProject;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<Void> deleteProject(DeleteProjectShardRequest request);
}
