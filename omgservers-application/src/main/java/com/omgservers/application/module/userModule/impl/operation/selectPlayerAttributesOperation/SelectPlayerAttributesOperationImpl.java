package com.omgservers.application.module.userModule.impl.operation.selectPlayerAttributesOperation;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerAttributesOperationImpl implements SelectPlayerAttributesOperation {

    static private final String sql = """
            select id, player_id, created, modified, attribute_name, attribute_value
            from $schema.tab_player_attribute
            where player_id = $1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<List<AttributeModel>> selectPlayerAttributes(final SqlConnection sqlConnection,
                                                            final int shard,
                                                            final Long playerId) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (playerId == null) {
            throw new IllegalArgumentException("playerId is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(playerId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final var result = new ArrayList<AttributeModel>();
                    while (iterator.hasNext()) {
                        final var attribute = createAttribute(iterator.next());
                        result.add(attribute);
                    }
                    if (result.size() > 0) {
                        log.info("Player's attributes were found, playerId={}, size={}", playerId, result.size());
                    } else {
                        log.info("Player's attributes were not found, playerId={}", playerId);
                    }

                    return result;
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
