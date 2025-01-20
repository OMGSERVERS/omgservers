package com.omgservers.service.shard.queue.impl.operation.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.service.shard.queue.impl.mappers.QueueRequestModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectQueueRequestOperationImpl implements SelectQueueRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final QueueRequestModelMapper queueRequestModelMapper;

    @Override
    public Uni<QueueRequestModel> execute(final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long queueId,
                                          final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, queue_id, created, modified, client_id, deleted
                        from $schema.tab_queue_request
                        where queue_id = $1 and id = $2
                        limit 1
                        """,
                List.of(queueId, id),
                "Queue request",
                queueRequestModelMapper::execute);
    }
}
