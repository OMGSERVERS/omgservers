package com.omgservers.application.module.userModule.impl.operation.selectObjectOperation;

import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.object.ObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectObjectOperationImpl implements SelectObjectOperation {

    static private final String sql = """
            select id, player_id, created, modified, name, body
            from $schema.tab_user_object
            where player_id = $1 and name = $2
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<ObjectModel> selectObject(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long playerId,
                                         final String name) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (playerId == null) {
            throw new IllegalArgumentException("playerId is null");
        }
        if (name == null) {
            throw new IllegalArgumentException("fileName is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(playerId, name))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        log.info("Object was found, playerId={}, name={}", playerId, name);
                        return createObject(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException(String.format("object was not found, " +
                                "playerId=%s, name=%s", playerId, name));
                    }
                });
    }

    ObjectModel createObject(Row row) {
        ObjectModel object = new ObjectModel();
        object.setId(row.getLong("id"));
        object.setPlayerId(row.getLong("player_id"));
        object.setCreated(row.getOffsetDateTime("created").toInstant());
        object.setModified(row.getOffsetDateTime("modified").toInstant());
        object.setName(row.getString("name"));
        object.setBody(row.getBuffer("body").getBytes());
        return object;
    }
}
