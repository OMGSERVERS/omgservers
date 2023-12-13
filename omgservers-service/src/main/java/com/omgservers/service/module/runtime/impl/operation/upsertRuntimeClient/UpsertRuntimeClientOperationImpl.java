package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimeClientOperationImpl implements UpsertRuntimeClientOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeClient(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final RuntimeClientModel runtimeClient) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_client(
                            id, runtime_id, created, modified, user_id, client_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        runtimeClient.getId(),
                        runtimeClient.getRuntimeId(),
                        runtimeClient.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeClient.getModified().atOffset(ZoneOffset.UTC),
                        runtimeClient.getUserId(),
                        runtimeClient.getClientId(),
                        runtimeClient.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
