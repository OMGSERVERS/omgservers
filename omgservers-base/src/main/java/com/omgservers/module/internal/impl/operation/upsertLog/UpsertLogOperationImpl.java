package com.omgservers.module.internal.impl.operation.upsertLog;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.log.LogModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
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
class UpsertLogOperationImpl implements UpsertLogOperation {

    private static final String SQL = """
            insert into internal.tab_log(id, created, message)
            values($1, $2, $3)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertLog(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final LogModel logModel) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (logModel == null) {
            throw new ServerSideBadRequestException("log is null");
        }

        return upsertObject(sqlConnection, logModel)
                .invoke(logWasInserted -> {
                    if (logWasInserted) {
                        changeContext.add(logModel);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(final SqlConnection sqlConnection, final LogModel logModel) {
        return sqlConnection.preparedQuery(SQL)
                .execute(Tuple.from(Arrays.asList(
                        logModel.getId(),
                        logModel.getCreated().atOffset(ZoneOffset.UTC),
                        logModel.getMessage()
                )))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
