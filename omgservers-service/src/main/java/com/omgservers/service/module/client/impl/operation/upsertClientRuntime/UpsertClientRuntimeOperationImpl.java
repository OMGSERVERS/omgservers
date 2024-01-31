package com.omgservers.service.module.client.impl.operation.upsertClientRuntime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.model.event.body.ClientRuntimeCreatedEventBodyModel;
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
class UpsertClientRuntimeOperationImpl implements UpsertClientRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClientRuntime(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final ClientRuntimeModel clientRuntime) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_client_runtime(
                            id, client_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        clientRuntime.getId(),
                        clientRuntime.getClientId(),
                        clientRuntime.getCreated().atOffset(ZoneOffset.UTC),
                        clientRuntime.getModified().atOffset(ZoneOffset.UTC),
                        clientRuntime.getRuntimeId(),
                        clientRuntime.getDeleted()
                ),
                () -> new ClientRuntimeCreatedEventBodyModel(clientRuntime.getClientId(), clientRuntime.getId()),
                () -> null
        );
    }
}
