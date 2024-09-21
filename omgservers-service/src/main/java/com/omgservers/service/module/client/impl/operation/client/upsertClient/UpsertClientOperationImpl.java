package com.omgservers.service.module.client.impl.operation.client.upsertClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
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
class UpsertClientOperationImpl implements UpsertClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClient(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final ClientModel client) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_client(
                            id, idempotency_key, created, modified, user_id, player_id, tenant_id, version_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        client.getId(),
                        client.getIdempotencyKey(),
                        client.getCreated().atOffset(ZoneOffset.UTC),
                        client.getModified().atOffset(ZoneOffset.UTC),
                        client.getUserId(),
                        client.getPlayerId(),
                        client.getTenantId(),
                        client.getDeploymentId(),
                        client.getDeleted()
                ),
                () -> new ClientCreatedEventBodyModel(client.getId()),
                () -> null
        );
    }
}
