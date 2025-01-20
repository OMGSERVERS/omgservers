package com.omgservers.service.shard.queue.impl.operation.queue;

import com.omgservers.schema.model.queue.QueueModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectQueueOperation {
    Uni<QueueModel> execute(SqlConnection sqlConnection,
                            int shard,
                            Long id);
}
