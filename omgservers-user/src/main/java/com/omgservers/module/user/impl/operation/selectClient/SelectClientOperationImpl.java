package com.omgservers.module.user.impl.operation.selectClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectClientOperationImpl implements SelectClientOperation {

    final SelectObjectOperation selectObjectOperation;

    @Override
    public Uni<ClientModel> selectClient(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long userId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, user_id, player_id, created, server, connection_id, script_id
                        from $schema.tab_user_client
                        where user_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(userId, id),
                "Client",
                this::createClient);
    }

    ClientModel createClient(Row row) {
        ClientModel client = new ClientModel();
        client.setId(row.getLong("id"));
        client.setUserId(row.getLong("user_id"));
        client.setPlayerId(row.getLong("player_id"));
        client.setCreated(row.getOffsetDateTime("created").toInstant());
        client.setServer(URI.create(row.getString("server")));
        client.setConnectionId(row.getLong("connection_id"));
        client.setScriptId(row.getLong("script_id"));
        return client;
    }
}
