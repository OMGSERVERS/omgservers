package com.omgservers.service.shard.queue.impl.operation.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertQueueOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         QueueModel queue);
}
