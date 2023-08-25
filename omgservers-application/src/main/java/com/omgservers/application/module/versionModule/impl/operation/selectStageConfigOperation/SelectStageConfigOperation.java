package com.omgservers.application.module.versionModule.impl.operation.selectStageConfigOperation;

import com.omgservers.model.version.VersionStageConfigModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectStageConfigOperation {
    Uni<VersionStageConfigModel> selectStageConfig(SqlConnection sqlConnection,
                                                   int shard,
                                                   Long versionId);

    default VersionStageConfigModel selectStageConfig(long timeout, PgPool pgPool, int shard, Long versionId) {
        return pgPool.withTransaction(sqlConnection -> selectStageConfig(sqlConnection, shard, versionId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
