package com.omgservers.service.shard.queue.impl.operation.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.service.event.body.module.queue.QueueCreatedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertQueueOperationImpl implements UpsertQueueOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final QueueModel queue) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_queue(
                            id, idempotency_key, created, modified, tenant_id, deployment_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        queue.getId(),
                        queue.getIdempotencyKey(),
                        queue.getCreated().atOffset(ZoneOffset.UTC),
                        queue.getModified().atOffset(ZoneOffset.UTC),
                        queue.getTenantId(),
                        queue.getDeploymentId(),
                        queue.getDeleted()
                ),
                () -> new QueueCreatedEventBodyModel(queue.getId()),
                () -> null
        );
    }
}
