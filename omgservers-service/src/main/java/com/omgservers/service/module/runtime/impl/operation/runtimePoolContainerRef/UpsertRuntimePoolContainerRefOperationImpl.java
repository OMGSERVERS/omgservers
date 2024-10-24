package com.omgservers.service.module.runtime.impl.operation.runtimePoolContainerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.service.event.body.module.runtime.RuntimePoolContainerRefCreatedEventBodyModel;
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
class UpsertRuntimePoolContainerRefOperationImpl implements UpsertRuntimePoolContainerRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final RuntimePoolContainerRefModel runtimePoolContainerRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_pool_container_ref(
                            id, idempotency_key, runtime_id, created, modified, pool_id, server_id, container_id,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtimePoolContainerRef.getId(),
                        runtimePoolContainerRef.getIdempotencyKey(),
                        runtimePoolContainerRef.getRuntimeId(),
                        runtimePoolContainerRef.getCreated().atOffset(ZoneOffset.UTC),
                        runtimePoolContainerRef.getModified().atOffset(ZoneOffset.UTC),
                        runtimePoolContainerRef.getPoolId(),
                        runtimePoolContainerRef.getServerId(),
                        runtimePoolContainerRef.getContainerId(),
                        runtimePoolContainerRef.getDeleted()
                ),
                () -> new RuntimePoolContainerRefCreatedEventBodyModel(
                        runtimePoolContainerRef.getRuntimeId(),
                        runtimePoolContainerRef.getId()),
                () -> null
        );
    }
}
