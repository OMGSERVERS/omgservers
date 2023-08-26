package com.omgservers.application.module.userModule.impl.operation.selectAttributeOperation;

import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.attribute.AttributeModel;
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
class SelectAttributeOperationImpl implements SelectAttributeOperation {

    static private final String sql = """
            select id, player_id, created, modified, attribute_name, attribute_value
            from $schema.tab_user_attribute a
            where player_id = $1 and attribute_name = $2
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<AttributeModel> selectAttribute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long playerId,
                                               final String name) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (playerId == null) {
            throw new ServerSideBadRequestException("playerId is null");
        }
        if (name == null) {
            throw new ServerSideBadRequestException("name is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(playerId, name))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        log.info("Attribute was found, playerId={}, name={}", playerId, name);
                        return createAttribute(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException(String.format("attribute or player was not found, " +
                                "playerId=%s, name=%s", playerId, name));
                    }
                });
    }

    AttributeModel createAttribute(Row row) {
        AttributeModel attribute = new AttributeModel();
        attribute.setId(row.getLong("id"));
        attribute.setPlayerId(row.getLong("player_id"));
        attribute.setCreated(row.getOffsetDateTime("created").toInstant());
        attribute.setModified(row.getOffsetDateTime("modified").toInstant());
        attribute.setName(row.getString("attribute_name"));
        attribute.setValue(row.getString("attribute_value"));
        return attribute;
    }
}
