package com.omgservers.service.operation.hasObject;

import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.service.operation.transformPgException.TransformPgExceptionOperation;
import com.omgservers.service.operation.upsertEvent.UpsertEventOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasObjectOperationImpl implements HasObjectOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> hasObject(final SqlConnection sqlConnection,
                                  final int shard,
                                  final String sql,
                                  final List<?> parameters,
                                  final String objectName) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(rowSet -> rowSet.rowCount() > 0)
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
