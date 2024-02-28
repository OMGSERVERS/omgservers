package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.RuntimeClientCreatedEventBodyModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
                            id, runtime_id, created, modified, client_id, last_activity, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        runtimeClient.getId(),
                        runtimeClient.getRuntimeId(),
                        runtimeClient.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeClient.getModified().atOffset(ZoneOffset.UTC),
                        runtimeClient.getClientId(),
                        runtimeClient.getLastActivity().atOffset(ZoneOffset.UTC),
                        getConfigString(runtimeClient),
                        runtimeClient.getDeleted()
                ),
                () -> new RuntimeClientCreatedEventBodyModel(runtimeClient.getRuntimeId(),
                        runtimeClient.getId()),
                () -> null
        );
    }

    String getConfigString(RuntimeClientModel runtimeClient) {
        try {
            return objectMapper.writeValueAsString(runtimeClient.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
