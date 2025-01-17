package com.omgservers.service.module.queue.impl.operation.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.service.module.queue.impl.mappers.QueueRequestModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectQueueRequestByQueueIdAndClientIdOperationImpl
        implements SelectQueueRequestByQueueIdAndClientIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final QueueRequestModelMapper queueRequestModelMapper;

    @Override
    public Uni<QueueRequestModel> execute(final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long queueId,
                                          final Long clientId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, queue_id, created, modified, client_id, deleted
                        from $schema.tab_queue_request
                        where queue_id = $1 and client_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(queueId, clientId),
                "Queue request",
                queueRequestModelMapper::execute);
    }
}
