package com.omgservers.service.shard.queue.impl.operation.queueRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.queueRequest.QueueRequestModel;
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
class UpsertQueueRequestOperationImpl implements UpsertQueueRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final QueueRequestModel queueRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_queue_request(
                            id, idempotency_key, queue_id, created, modified, client_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        queueRequest.getId(),
                        queueRequest.getIdempotencyKey(),
                        queueRequest.getQueueId(),
                        queueRequest.getCreated().atOffset(ZoneOffset.UTC),
                        queueRequest.getModified().atOffset(ZoneOffset.UTC),
                        queueRequest.getClientId(),
                        queueRequest.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
