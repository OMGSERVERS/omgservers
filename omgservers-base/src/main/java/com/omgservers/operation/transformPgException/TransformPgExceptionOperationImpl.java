package com.omgservers.operation.transformPgException;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class TransformPgExceptionOperationImpl implements TransformPgExceptionOperation {

    @Override
    public RuntimeException transformPgException(PgException pgException) {
        final var code = pgException.getSqlState();
        return switch (code) {
            // foreign_key_violation
            case "23503" -> new ServerSideNotFoundException("foreign key violation, " +
                    "constraint=" + pgException.getConstraint(), pgException);
            // unique_violation
            case "23505" -> new ServerSideConflictException("unique violation, " +
                    "constraint=" + pgException.getConstraint(), pgException);
            // not_null_violation
            case "23502" -> new ServerSideBadRequestException("not null violation, " +
                    "constraint=" + pgException.getConstraint(), pgException);
            default -> new ServerSideInternalException("unhandled PgException, " + pgException.getMessage(),
                    pgException);
        };
    }
}
