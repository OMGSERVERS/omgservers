package com.omgservers.service.module.queue.impl.operation.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertQueueRequestOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         QueueRequestModel queueRequest);
}
