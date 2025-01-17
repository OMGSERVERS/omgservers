package com.omgservers.service.module.queue.impl.operation.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.service.module.queue.impl.mappers.QueueModelMapper;
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
class SelectQueueOperationImpl implements SelectQueueOperation {

    final SelectObjectOperation selectObjectOperation;

    final QueueModelMapper queueModelMapper;

    @Override
    public Uni<QueueModel> execute(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, tenant_id, deployment_id, deleted
                        from $schema.tab_queue
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Queue",
                queueModelMapper::execute);
    }
}
