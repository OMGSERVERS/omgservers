package com.omgservers.module.user.impl.operation.upsertAttribute;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
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
class UpsertAttributeOperationImpl implements UpsertAttributeOperation {

    static private final String sql = """
            insert into $schema.tab_user_attribute(id, player_id, created, modified, attribute_name, attribute_value)
            values($1, $2, $3, $4, $5, $6)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertAttribute(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int shard,
                                        final AttributeModel attribute) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (attribute == null) {
            throw new ServerSideBadRequestException("attribute is null");
        }

        return upsertObject(sqlConnection, shard, attribute)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, attribute))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, attribute))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Attribute was inserted, attribute={}", attribute);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, AttributeModel attribute) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        attribute.getId(),
                        attribute.getPlayerId(),
                        attribute.getCreated().atOffset(ZoneOffset.UTC),
                        attribute.getModified().atOffset(ZoneOffset.UTC),
                        attribute.getName(),
                        attribute.getValue()
                )))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final AttributeModel attribute) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final AttributeModel attribute) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Attribute was inserted, attribute=" + attribute);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
