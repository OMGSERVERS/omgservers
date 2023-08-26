package com.omgservers.application.module.userModule.impl.operation.selectClientOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectClientOperationImpl implements SelectClientOperation {

    static private final String sql = """
            select id, player_id, created, server, connection_id
            from $schema.tab_user_client
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<ClientModel> selectClient(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var client = createClient(iterator.next());
                        log.info("Client was found, client={}", client);
                        return client;
                    } else {
                        log.info("Client was not found, id={}", id);
                        throw new ServerSideNotFoundException(String.format("client was not found, id=%s", id));
                    }
                });
    }

    ClientModel createClient(Row row) {
        ClientModel client = new ClientModel();
        client.setId(row.getLong("id"));
        client.setPlayerId(row.getLong("player_id"));
        client.setCreated(row.getOffsetDateTime("created").toInstant());
        client.setServer(URI.create(row.getString("server")));
        client.setConnectionId(row.getLong("connection_id"));
        return client;
    }
}
