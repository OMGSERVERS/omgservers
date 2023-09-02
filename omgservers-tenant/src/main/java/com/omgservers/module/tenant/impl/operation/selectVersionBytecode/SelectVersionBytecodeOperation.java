package com.omgservers.module.tenant.impl.operation.selectVersionBytecode;

import com.omgservers.model.version.VersionBytecodeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectVersionBytecodeOperation {
    Uni<VersionBytecodeModel> selectVersionBytecode(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long versionId);

    default VersionBytecodeModel selectVersionBytecode(long timeout, PgPool pgPool, int shard, Long versionId) {
        return pgPool.withTransaction(sqlConnection -> selectVersionBytecode(sqlConnection, shard, versionId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
