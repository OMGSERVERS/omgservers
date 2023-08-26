package com.omgservers.module.internal.impl.operation.upsertLog;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertLogOperationImpl implements UpsertLogOperation {

    static private final String sql = """
            insert into internal.tab_log(id, created, message)
            values($1, $2, $3)
            on conflict (id) do
            update set message = $3
            returning xmax::text::int = 0 as inserted
            """;

    @Override
    public Uni<Boolean> upsertLog(final SqlConnection sqlConnection, final LogModel logModel) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (logModel == null) {
            throw new ServerSideBadRequestException("log is null");
        }

        return upsertQuery(sqlConnection, logModel)
                .invoke(voidItem -> log.info("Log was inserted, log={}", logModel))
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, log=%s", t.getMessage(), logModel)));
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection, final LogModel logModel) {
        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(logModel.getId());
                    add(logModel.getCreated().atOffset(ZoneOffset.UTC));
                    add(logModel.getMessage());
                }}))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
