package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.deletePoolServer;

import com.omgservers.model.dto.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.DeletePoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerMethod {
    Uni<DeletePoolServerResponse> deletePoolServer(DeletePoolServerRequest request);
}
