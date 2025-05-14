package com.omgservers.service.master.task.impl.operation;

import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.event.body.system.TaskCreatedEventBodyModel;
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
class UpsertJobOperationImpl implements UpsertJobOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertJob(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final TaskModel task) {
        return changeObjectOperation.execute(
                changeContext,
                sqlConnection,
                """
                        insert into $master.tab_task(
                            id, idempotency_key, created, modified, qualifier, shard_key, entity_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        task.getId(),
                        task.getIdempotencyKey(),
                        task.getCreated().atOffset(ZoneOffset.UTC),
                        task.getModified().atOffset(ZoneOffset.UTC),
                        task.getQualifier(),
                        task.getShardKey(),
                        task.getEntityId(),
                        task.getDeleted()
                ),
                () -> new TaskCreatedEventBodyModel(task.getId()),
                () -> null
        );
    }
}
