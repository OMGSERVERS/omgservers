package com.omgservers.service.module.user.impl.operation.upsertClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
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
class UpsertClientOperationImpl implements UpsertClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertClient(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final ClientModel client) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_client(
                            id, user_id, player_id, created, modified, server, connection_id, version_id, default_matchmaker_id, default_runtime_id)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        client.getId(),
                        client.getUserId(),
                        client.getPlayerId(),
                        client.getCreated().atOffset(ZoneOffset.UTC),
                        client.getModified().atOffset(ZoneOffset.UTC),
                        client.getServer().toString(),
                        client.getConnectionId(),
                        client.getVersionId(),
                        client.getDefaultMatchmakerId(),
                        client.getDefaultRuntimeId()
                ),
                () -> new ClientCreatedEventBodyModel(client.getUserId(), client.getId()),
                () -> logModelFactory.create(String.format("Client was inserted, " +
                        "client=%s", client))
        );
    }
}
