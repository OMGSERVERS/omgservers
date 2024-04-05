package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.upsertRuntimePoolServerContainerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.runtime.RuntimePoolServerContainerRefCreatedEventBodyModel;
import com.omgservers.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
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
class UpsertRuntimePoolServerContainerRefOperationImpl implements UpsertRuntimePoolServerContainerRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimePoolServerContainerRef(final ChangeContext<?> changeContext,
                                                            final SqlConnection sqlConnection,
                                                            final int shard,
                                                            final RuntimePoolServerContainerRefModel runtimePoolServerContainerRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_pool_server_container_ref(
                            id, idempotency_key, runtime_id, created, modified, pool_id, server_id, container_id,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtimePoolServerContainerRef.getId(),
                        runtimePoolServerContainerRef.getIdempotencyKey(),
                        runtimePoolServerContainerRef.getRuntimeId(),
                        runtimePoolServerContainerRef.getCreated().atOffset(ZoneOffset.UTC),
                        runtimePoolServerContainerRef.getModified().atOffset(ZoneOffset.UTC),
                        runtimePoolServerContainerRef.getPoolId(),
                        runtimePoolServerContainerRef.getServerId(),
                        runtimePoolServerContainerRef.getContainerId(),
                        runtimePoolServerContainerRef.getDeleted()
                ),
                () -> new RuntimePoolServerContainerRefCreatedEventBodyModel(
                        runtimePoolServerContainerRef.getRuntimeId(),
                        runtimePoolServerContainerRef.getId()),
                () -> null
        );
    }
}
