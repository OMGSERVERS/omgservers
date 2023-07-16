package com.omgservers.application.module.userModule.impl.operation.selectAttributeOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAttributeOperationImpl implements SelectAttributeOperation {

    static private final String sql = """
            select player_uuid, created, modified, attribute_name, attribute_value
            from $schema.tab_player_attribute a
            where player_uuid = $1 and attribute_name = $2
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<AttributeModel> selectAttribute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final UUID player,
                                               final String name) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (player == null) {
            throw new ServerSideBadRequestException("player is null");
        }
        if (name == null) {
            throw new ServerSideBadRequestException("name is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(player, name))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        log.info("Attribute was found, player={}, name={}", player, name);
                        return createAttribute(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException(String.format("attribute or player was not found, " +
                                "player=%s, name=%s", player, name));
                    }
                });
    }

    AttributeModel createAttribute(Row row) {
        AttributeModel attribute = new AttributeModel();
        attribute.setPlayer(row.getUUID("player_uuid"));
        attribute.setCreated(row.getOffsetDateTime("created").toInstant());
        attribute.setModified(row.getOffsetDateTime("modified").toInstant());
        attribute.setName(row.getString("attribute_name"));
        attribute.setValue(row.getString("attribute_value"));
        return attribute;
    }
}
