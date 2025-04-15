package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.LockQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.RowSet;
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
class TryAdvisoryLockOperationImpl implements TryAdvisoryLockOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection, final LockQualifierEnum lock) {
        final var sql = "select pg_try_advisory_xact_lock($1)";
        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.from(List.of(lock.getKey())))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var row = iterator.next();
                        final var acquired = row.getBoolean("pg_try_advisory_xact_lock");
                        if (acquired) {
                            log.debug("Lock \"{}\" acquired", lock);
                        } else {
                            log.debug("Lock \"{}\" not acquired", lock);
                        }
                        return acquired;
                    } else {
                        log.error("PostgreSQL function \"pg_try_advisory_xact_lock()\" returned no result");
                        return Boolean.FALSE;
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(sql, (PgException) t));
    }
}
