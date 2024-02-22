package com.omgservers.service.module.client.impl.operation.upsertClientRuntimeRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import com.omgservers.model.event.body.ClientRuntimeRefCreatedEventBodyModel;
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
class UpsertClientRuntimeRefOperationImpl implements UpsertClientRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClientRuntimeRef(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final ClientRuntimeRefModel clientRuntimeRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_client_runtime_ref(
                            id, client_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        clientRuntimeRef.getId(),
                        clientRuntimeRef.getClientId(),
                        clientRuntimeRef.getCreated().atOffset(ZoneOffset.UTC),
                        clientRuntimeRef.getModified().atOffset(ZoneOffset.UTC),
                        clientRuntimeRef.getRuntimeId(),
                        clientRuntimeRef.getDeleted()
                ),
                () -> new ClientRuntimeRefCreatedEventBodyModel(clientRuntimeRef.getClientId(),
                        clientRuntimeRef.getId()),
                () -> null
        );
    }
}
