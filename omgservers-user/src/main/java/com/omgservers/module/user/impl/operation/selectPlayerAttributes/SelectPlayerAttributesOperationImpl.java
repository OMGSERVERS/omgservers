package com.omgservers.module.user.impl.operation.selectPlayerAttributes;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerAttributesOperationImpl implements SelectPlayerAttributesOperation {

    private static final String SQL = """
            select id, player_id, created, modified, attribute_name, attribute_value
            from $schema.tab_user_attribute
            where player_id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
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

        String preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(playerId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final List<AttributeModel> result = new ArrayList<AttributeModel>();
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
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
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
