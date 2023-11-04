package com.omgservers.service.module.matchmaker.impl.operation.upsertRequest;

import com.omgservers.model.request.RequestModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRequestOperation {
    Uni<Boolean> upsertRequest(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               RequestModel request);
}
