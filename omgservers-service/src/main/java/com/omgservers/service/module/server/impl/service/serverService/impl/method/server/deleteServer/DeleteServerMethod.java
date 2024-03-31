package com.omgservers.service.module.server.impl.service.serverService.impl.method.server.deleteServer;

import com.omgservers.model.dto.server.DeleteServerRequest;
import com.omgservers.model.dto.server.DeleteServerResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteServerMethod {
    Uni<DeleteServerResponse> deleteServer(DeleteServerRequest request);
}
