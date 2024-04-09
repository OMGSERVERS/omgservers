package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.generateId;

import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import io.smallrye.mutiny.Uni;

public interface GenerateIdMethod {
    Uni<GenerateIdAdminResponse> getId();
}
