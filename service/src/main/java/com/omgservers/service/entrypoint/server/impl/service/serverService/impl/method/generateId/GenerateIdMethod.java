package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.generateId;

import com.omgservers.model.dto.server.GenerateIdServerResponse;
import io.smallrye.mutiny.Uni;

public interface GenerateIdMethod {
    Uni<GenerateIdServerResponse> getId();
}
