package com.omgservers.module.user.impl.operation.upsertClient;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.client.ClientModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertClientOperationImpl implements UpsertClientOperation {

    static private final String sql = """
            insert into $schema.tab_user_client(id, player_id, created, server, connection_id)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> upsertClient(final SqlConnection sqlConnection,
                                     final int shard,
                                     final ClientModel client) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (client == null) {
            throw new ServerSideBadRequestException("client is null");
        }

        return upsertQuery(sqlConnection, shard, client)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Client was created, client={}", client);
                    } else {
                        log.info("Client was updated, client={}", client);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, ClientModel client) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        client.getId(),
                        client.getPlayerId(),
                        client.getCreated().atOffset(ZoneOffset.UTC),
                        client.getServer().toString(),
                        client.getConnectionId()
                )))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
