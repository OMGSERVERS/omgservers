package com.omgservers.service.module.queue.impl.operation.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveQueueRequestsByQueueIdOperation {
    Uni<List<QueueRequestModel>> execute(SqlConnection sqlConnection,
                                         int shard,
                                         Long queueId);
}
