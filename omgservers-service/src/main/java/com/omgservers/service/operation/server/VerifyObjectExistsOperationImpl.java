package com.omgservers.service.operation.server;

import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.server.event.operation.UpsertEventOperation;
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
class VerifyObjectExistsOperationImpl implements VerifyObjectExistsOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareSlotSqlOperation prepareSlotSqlOperation;
    final UpsertEventOperation upsertEventOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int slot,
                                final String sql,
                                final List<?> parameters,
                                final String objectName) {
        var preparedSql = prepareSlotSqlOperation.execute(sql, slot);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(rowSet -> rowSet.rowCount() > 0)
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
