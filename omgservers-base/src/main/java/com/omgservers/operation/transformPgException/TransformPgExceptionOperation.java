package com.omgservers.operation.transformPgException;

import io.vertx.pgclient.PgException;

public interface TransformPgExceptionOperation {
    RuntimeException transformPgException(PgException pgException);
}
