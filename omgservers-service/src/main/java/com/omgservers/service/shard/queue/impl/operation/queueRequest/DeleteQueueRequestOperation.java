package com.omgservers.service.shard.queue.impl.operation.queueRequest;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteQueueRequestOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         Long queueId,
                         Long id);
}
