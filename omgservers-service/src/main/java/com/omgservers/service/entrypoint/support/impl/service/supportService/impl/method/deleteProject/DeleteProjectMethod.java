package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProject;

import com.omgservers.schema.entrypoint.support.DeleteProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<DeleteProjectSupportResponse> deleteProject(DeleteProjectSupportRequest request);
}
