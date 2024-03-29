package com.omgservers.service.module.pool.impl.operation.upsertPool;

import com.omgservers.model.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertPoolOperationImpl implements UpsertPoolOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertPool(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final PoolModel pool) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool(
                            id, idempotency_key, created, modified, root_id, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        pool.getId(),
                        pool.getIdempotencyKey(),
                        pool.getCreated().atOffset(ZoneOffset.UTC),
                        pool.getModified().atOffset(ZoneOffset.UTC),
                        pool.getRootId(),
                        pool.getDeleted()
                ),
                () -> new PoolCreatedEventBodyModel(pool.getId()),
                () -> null
        );
    }
}
