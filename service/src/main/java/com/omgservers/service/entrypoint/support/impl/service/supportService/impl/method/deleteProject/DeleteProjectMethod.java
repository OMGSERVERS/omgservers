package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProject;

import com.omgservers.model.dto.support.DeleteProjectSupportRequest;
import com.omgservers.model.dto.support.DeleteProjectSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<DeleteProjectSupportResponse> deleteProject(DeleteProjectSupportRequest request);
}
