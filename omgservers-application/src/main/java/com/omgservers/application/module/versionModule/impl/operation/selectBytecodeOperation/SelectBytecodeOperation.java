package com.omgservers.application.module.versionModule.impl.operation.selectBytecodeOperation;

import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectBytecodeOperation {
    Uni<VersionBytecodeModel> selectBytecode(SqlConnection sqlConnection,
                                             int shard,
                                             UUID version);

    default VersionBytecodeModel selectBytecode(long timeout, PgPool pgPool, int shard, UUID version) {
        return pgPool.withTransaction(sqlConnection -> selectBytecode(sqlConnection, shard, version))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
