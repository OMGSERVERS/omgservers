package com.omgservers.service.operation.transformPgException;

import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideInternalException;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class TransformPgExceptionOperationImpl implements TransformPgExceptionOperation {

    @Override
    public RuntimeException transformPgException(PgException pgException) {
        return transformPgException("", pgException);
    }

    @Override
    public RuntimeException transformPgException(String sql, PgException pgException) {
        final var code = pgException.getSqlState();
        return switch (code) {
            // foreign_key_violation
            case "23503" -> new ServerSideConflictException("foreign key violation, " +
                    "constraint=" + pgException.getConstraint() +
                    ", sql=" + sql.replaceAll(System.lineSeparator(), " "),
                    pgException);
            // unique_violation
            case "23505" -> new ServerSideConflictException("unique violation, " +
                    "errorMessage=" + pgException.getErrorMessage() +
                    ", sql=" + sql.replaceAll(System.lineSeparator(), " "),
                    pgException);
            // not_null_violation
            case "23502" -> new ServerSideConflictException("not null violation, " +
                    "errorMessage=" + pgException.getErrorMessage(), pgException);
            default -> new ServerSideInternalException("unhandled PgException, " +
                    pgException.getErrorMessage() +
                    ", sql=" + sql.replaceAll(System.lineSeparator(), " "),
                    pgException);
        };
    }
}
