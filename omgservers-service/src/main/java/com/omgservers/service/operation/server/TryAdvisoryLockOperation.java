package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.LockQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface TryAdvisoryLockOperation {

    Uni<Boolean> execute(SqlConnection sqlConnection, LockQualifierEnum lock);
}
