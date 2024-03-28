package com.omgservers.service.module.pool.impl.operation.upsertPoolServerRef;

import com.omgservers.model.event.body.module.pool.PoolServerRefCreatedEventBodyModel;
import com.omgservers.model.poolServerRef.PoolServerRefModel;
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
class UpsertPoolServerRefOperationImpl implements UpsertPoolServerRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertPoolServerRef(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final PoolServerRefModel poolServerRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_server_ref(
                            id, idempotency_key, pool_id, created, modified, server_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolServerRef.getId(),
                        poolServerRef.getIdempotencyKey(),
                        poolServerRef.getPoolId(),
                        poolServerRef.getCreated().atOffset(ZoneOffset.UTC),
                        poolServerRef.getModified().atOffset(ZoneOffset.UTC),
                        poolServerRef.getServerId(),
                        poolServerRef.getDeleted()
                ),
                () -> new PoolServerRefCreatedEventBodyModel(poolServerRef.getPoolId(), poolServerRef.getId()),
                () -> null
        );
    }
}
