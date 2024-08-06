package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.generateId;

import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import io.smallrye.mutiny.Uni;

public interface GenerateIdMethod {
    Uni<GenerateIdServerResponse> getId();
}
