package com.omgservers.application.module.userModule.impl.operation.insertClientOperation;

import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InsertClientOperationImpl implements InsertClientOperation {

    static private final String sql = """
            insert into $schema.tab_player_client(id, player_id, created, server, connection_id)
            values($1, $2, $3, $4, $5)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Void> insertClient(final SqlConnection sqlConnection,
                                  final int shard,
                                  final ClientModel client) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (client == null) {
            throw new ServerSideBadRequestException("client is null");
        }

        return insertQuery(sqlConnection, shard, client)
                .invoke(voidItem -> log.info("Client was inserted, client={}", client))
                // TODO: use this handler for other operations
                .onFailure(PgException.class)
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getSqlState();
                    if (code.equals("23503")) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("player was not found, id=" + client.getPlayerId());
                    } else if (code.equals("23505")) {
                        // unique_violation
                        return new ServerSideConflictException("client already exists, id=" + client.getId());
                    } else {
                        return new ServerSideConflictException("unhandled PgException, " + t.getMessage());
                    }
                });
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, ClientModel client) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(client.getId());
                    add(client.getPlayerId());
                    add(client.getCreated().atOffset(ZoneOffset.UTC));
                    add(client.getServer().toString());
                    add(client.getConnectionId());
                }}))
                .replaceWithVoid();
    }
}
