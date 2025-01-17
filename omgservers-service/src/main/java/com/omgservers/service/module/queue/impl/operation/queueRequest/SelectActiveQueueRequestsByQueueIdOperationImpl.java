package com.omgservers.service.module.queue.impl.operation.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.service.module.queue.impl.mappers.QueueRequestModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveQueueRequestsByQueueIdOperationImpl
        implements SelectActiveQueueRequestsByQueueIdOperation {

    final SelectListOperation selectListOperation;

    final QueueRequestModelMapper queueRequestModelMapper;

    @Override
    public Uni<List<QueueRequestModel>> execute(final SqlConnection sqlConnection,
                                                final int shard,
                                                final Long queueId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, queue_id, created, modified, client_id, deleted
                        from $schema.tab_queue_request
                        where queue_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(queueId),
                "Queue request",
                queueRequestModelMapper::execute);
    }
}
