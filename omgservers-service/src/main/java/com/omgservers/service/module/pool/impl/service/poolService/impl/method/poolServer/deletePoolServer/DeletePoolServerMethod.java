package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.deletePoolServer;

import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerMethod {
    Uni<DeletePoolServerResponse> deletePoolServer(DeletePoolServerRequest request);
}
