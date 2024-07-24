package com.omgservers.service.operation.transformPgException;

import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideInternalException;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class TransformPgExceptionOperationImpl implements TransformPgExceptionOperation {

    @Override
    public RuntimeException transformPgException(final PgException pgException) {
        return transformPgException("", pgException);
    }

    @Override
    public RuntimeException transformPgException(final String sql, final PgException pgException) {
        final var code = pgException.getSqlState();
        return switch (code) {
            // foreign_key_violation
            case "23503" -> new ServerSideBadRequestException(ExceptionQualifierEnum.DB_CONSTRAINT_VIOLATED,
                    "foreign key violation, constraint=" + pgException.getConstraint() + ", sql=" +
                            sql.replaceAll(System.lineSeparator(), " "), pgException);
            // unique_violation
            case "23505" -> {
                // Using default name template for constraints
                final String idempotencyKeyConstraintPrefix = pgException.getTable() + "_idempotency_key";
                if (pgException.getConstraint().startsWith(idempotencyKeyConstraintPrefix)) {
                    yield new ServerSideConflictException(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED,
                            "idempotency violation, errorMessage=" + pgException.getErrorMessage() + ", sql=" +
                                    sql.replaceAll(System.lineSeparator(), " "), pgException);
                } else {
                    yield new ServerSideBadRequestException(ExceptionQualifierEnum.DB_CONSTRAINT_VIOLATED,
                            "unique violation, errorMessage=" + pgException.getErrorMessage() + ", sql=" +
                                    sql.replaceAll(System.lineSeparator(), " "), pgException);
                }
            }
            // not_null_violation
            case "23502" -> new ServerSideBadRequestException(ExceptionQualifierEnum.DB_CONSTRAINT_VIOLATED,
                    "not null violation, errorMessage=" + pgException.getErrorMessage(), pgException);
            default -> new ServerSideInternalException(ExceptionQualifierEnum.DB_EXCEPTION_UNHANDLED,
                    "unhandled PgException, " + pgException.getErrorMessage() + ", sql=" +
                            sql.replaceAll(System.lineSeparator(), " "), pgException);
        };
    }
}
