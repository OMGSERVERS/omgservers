package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.upsertPoolRuntimeServerContainerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.pool.PoolRuntimeServerContainerRefCreatedEventBodyModel;
import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
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
class UpsertPoolRuntimeServerContainerRefOperationImpl implements UpsertPoolRuntimeServerContainerRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPoolRuntimeServerContainerRef(final ChangeContext<?> changeContext,
                                                            final SqlConnection sqlConnection,
                                                            final int shard,
                                                            final PoolRuntimeServerContainerRefModel poolRuntimeServerContainerRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_runtime_server_container_ref(
                            id, idempotency_key, pool_id, created, modified, runtime_id, server_id, contianer_id,
                            deleted
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolRuntimeServerContainerRef.getId(),
                        poolRuntimeServerContainerRef.getIdempotencyKey(),
                        poolRuntimeServerContainerRef.getPoolId(),
                        poolRuntimeServerContainerRef.getCreated().atOffset(ZoneOffset.UTC),
                        poolRuntimeServerContainerRef.getModified().atOffset(ZoneOffset.UTC),
                        poolRuntimeServerContainerRef.getRuntimeId(),
                        poolRuntimeServerContainerRef.getServerId(),
                        poolRuntimeServerContainerRef.getContainerId(),
                        poolRuntimeServerContainerRef.getDeleted()
                ),
                () -> new PoolRuntimeServerContainerRefCreatedEventBodyModel(
                        poolRuntimeServerContainerRef.getPoolId(),
                        poolRuntimeServerContainerRef.getId()),
                () -> null
        );
    }
}
