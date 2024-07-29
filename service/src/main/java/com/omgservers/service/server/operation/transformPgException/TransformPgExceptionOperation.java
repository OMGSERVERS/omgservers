package com.omgservers.service.server.operation.transformPgException;

import io.vertx.pgclient.PgException;

public interface TransformPgExceptionOperation {
    RuntimeException transformPgException(PgException pgException);

    RuntimeException transformPgException(String sql, PgException pgException);
}
