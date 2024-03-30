package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.upsertRuntimeServerContainerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.runtime.RuntimeServerContainerRefCreatedEventBodyModel;
import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import com.omgservers.service.factory.LogModelFactory;
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
class UpsertRuntimeServerContainerRefOperationImpl implements UpsertRuntimeServerContainerRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeServerContainerRef(final ChangeContext<?> changeContext,
                                                        final SqlConnection sqlConnection,
                                                        final int shard,
                                                        final RuntimeServerContainerRefModel runtimeServerContainerRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_server_container_ref(
                            id, idempotency_key, runtime_id, created, modified, server_id, server_container_id,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtimeServerContainerRef.getId(),
                        runtimeServerContainerRef.getIdempotencyKey(),
                        runtimeServerContainerRef.getRuntimeId(),
                        runtimeServerContainerRef.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeServerContainerRef.getModified().atOffset(ZoneOffset.UTC),
                        runtimeServerContainerRef.getServerId(),
                        runtimeServerContainerRef.getServerContainerId(),
                        runtimeServerContainerRef.getDeleted()
                ),
                () -> new RuntimeServerContainerRefCreatedEventBodyModel(runtimeServerContainerRef.getRuntimeId(),
                        runtimeServerContainerRef.getId()),
                () -> null
        );
    }
}
