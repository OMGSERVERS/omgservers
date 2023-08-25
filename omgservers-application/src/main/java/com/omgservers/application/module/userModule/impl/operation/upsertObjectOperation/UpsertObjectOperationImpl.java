package com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation;

import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.object.ObjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertObjectOperationImpl implements UpsertObjectOperation {

    static private final String sql = """
            insert into $schema.tab_user_object(id, player_id, created, modified, name, body)
            values($1, $2, $3, $4, $5, $6)
            on conflict (player_id, name) do
            update set modified = $3, name = $5, body = $6
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> upsertObject(final SqlConnection sqlConnection,
                                     final int shard,
                                     final ObjectModel object) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (object == null) {
            throw new ServerSideBadRequestException("stage is null");
        }

        return upsertQuery(sqlConnection, shard, object)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Object was inserted, object={}", object);
                    } else {
                        log.info("Object was updated, object={}", object);
                    }
                });
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, ObjectModel object) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(object.getId());
                    add(object.getPlayerId());
                    add(object.getCreated().atOffset(ZoneOffset.UTC));
                    add(object.getModified().atOffset(ZoneOffset.UTC));
                    add(object.getName());
                    add(object.getBody());
                }}))
                .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
    }
}
