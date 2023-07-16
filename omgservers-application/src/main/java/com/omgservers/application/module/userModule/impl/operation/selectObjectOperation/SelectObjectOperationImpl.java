package com.omgservers.application.module.userModule.impl.operation.selectObjectOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.userModule.model.object.ObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectObjectOperationImpl implements SelectObjectOperation {

    static private final String sql = """
            select player_uuid, created, modified, uuid as object_uuid, name, body
            from $schema.tab_player_object
            where player_uuid = $1 and name = $2
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<ObjectModel> selectObject(final SqlConnection sqlConnection,
                                         final int shard,
                                         final UUID player,
                                         final String name) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
        if (name == null) {
            throw new IllegalArgumentException("fileName is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(player, name))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        log.info("Object was found, player={}, name={}", player, name);
                        return createObject(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException(String.format("object was not found, " +
                                "player=%s, name=%s", player, name));
                    }
                });
    }

    ObjectModel createObject(Row row) {
        ObjectModel object = new ObjectModel();
        object.setPlayer(row.getUUID("player_uuid"));
        object.setCreated(row.getOffsetDateTime("created").toInstant());
        object.setModified(row.getOffsetDateTime("modified").toInstant());
        object.setUuid(row.getUUID("object_uuid"));
        object.setName(row.getString("name"));
        object.setBody(row.getBuffer("body").getBytes());
        return object;
    }
}
