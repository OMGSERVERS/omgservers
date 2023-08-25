package com.omgservers.application.module.versionModule.impl.operation.selectBytecodeOperation;

import com.omgservers.model.version.VersionBytecodeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectBytecodeOperation {
    Uni<VersionBytecodeModel> selectBytecode(SqlConnection sqlConnection,
                                             int shard,
                                             Long versionId);

    default VersionBytecodeModel selectBytecode(long timeout, PgPool pgPool, int shard, Long versionId) {
        return pgPool.withTransaction(sqlConnection -> selectBytecode(sqlConnection, shard, versionId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
