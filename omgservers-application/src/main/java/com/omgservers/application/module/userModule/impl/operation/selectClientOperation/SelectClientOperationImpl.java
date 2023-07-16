package com.omgservers.application.module.userModule.impl.operation.selectClientOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectClientOperationImpl implements SelectClientOperation {

    static private final String sql = """
            select player_uuid, created, uuid, server, connection_uuid
            from $schema.tab_player_client
            where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<ClientModel> selectClient(final SqlConnection sqlConnection,
                                         final int shard,
                                         final UUID uuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var client = createClient(iterator.next());
                        log.info("Client was found, client={}", client);
                        return client;
                    } else {
                        log.info("Client was not found, uuid={}", uuid);
                        throw new ServerSideNotFoundException(String.format("client was not found, uuid=%s", uuid));
                    }
                });
    }

    ClientModel createClient(Row row) {
        ClientModel client = new ClientModel();
        client.setPlayer(row.getUUID("player_uuid"));
        client.setCreated(row.getOffsetDateTime("created").toInstant());
        client.setUuid(row.getUUID("uuid"));
        client.setServer(URI.create(row.getString("server")));
        client.setConnection(row.getUUID("connection_uuid"));
        return client;
    }
}
