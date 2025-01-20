package com.omgservers.service.shard.queue.impl.operation.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectQueueRequestByQueueIdAndClientIdOperation {
    Uni<QueueRequestModel> execute(SqlConnection sqlConnection,
                                   int shard,
                                   Long queueId,
                                   Long clientId);
}
