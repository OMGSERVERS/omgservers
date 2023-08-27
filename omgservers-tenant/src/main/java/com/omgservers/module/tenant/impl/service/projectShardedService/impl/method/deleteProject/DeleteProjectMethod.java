package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.deleteProject;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<Void> deleteProject(DeleteProjectShardedRequest request);
}
