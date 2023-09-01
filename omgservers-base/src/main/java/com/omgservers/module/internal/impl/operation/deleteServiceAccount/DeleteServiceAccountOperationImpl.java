package com.omgservers.module.internal.impl.operation.deleteServiceAccount;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteServiceAccountOperationImpl implements DeleteServiceAccountOperation {

    static private final String sql = """
            delete from internal.tab_service_account where id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;

    @Override
    public Uni<Boolean> deleteServiceAccount(final SqlConnection sqlConnection, final Long id) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(id))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Service account was deleted, id={}", id);
                    } else {
                        log.warn("Service account was not found, skip operation, id={}", id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }
}
