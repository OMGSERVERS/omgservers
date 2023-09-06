package com.omgservers.module.user.impl.operation.upsertClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
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

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertClient(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final ClientModel client) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_client(id, player_id, created, server, connection_id)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        client.getId(),
                        client.getPlayerId(),
                        client.getCreated().atOffset(ZoneOffset.UTC),
                        client.getServer().toString(),
                        client.getConnectionId()
                ),
                () -> new ClientCreatedEventBodyModel(userId, client.getId()),
                () -> logModelFactory.create(String.format("Client was inserted, " +
                        "shard=%d, userId=%d, client=%s", shard, userId, client))
        );
    }
}
