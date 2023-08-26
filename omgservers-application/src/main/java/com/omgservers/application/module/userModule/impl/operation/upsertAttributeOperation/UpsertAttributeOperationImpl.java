package com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation;

import com.omgservers.base.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.attribute.AttributeModel;
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
class UpsertAttributeOperationImpl implements UpsertAttributeOperation {

    static private final String sql = """
            insert into $schema.tab_user_attribute(id, player_id, created, modified, attribute_name, attribute_value)
            values($1, $2, $3, $4, $5, $6)
            on conflict (player_id, attribute_name) do
            update set modified = $3, attribute_value = $5
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> upsertAttribute(final SqlConnection sqlConnection,
                                        final int shard,
                                        final AttributeModel attribute) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (attribute == null) {
            throw new ServerSideBadRequestException("attribute is null");
        }

        return upsertQuery(sqlConnection, shard, attribute)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Attribute was inserted, object={}", attribute);
                    } else {
                        log.info("Attribute was updated, object={}", attribute);
                    }
                });
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, AttributeModel attribute) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(attribute.getId());
                    add(attribute.getPlayerId());
                    add(attribute.getCreated().atOffset(ZoneOffset.UTC));
                    add(attribute.getModified().atOffset(ZoneOffset.UTC));
                    add(attribute.getName());
                    add(attribute.getValue());
                }}))
                .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
    }
}
