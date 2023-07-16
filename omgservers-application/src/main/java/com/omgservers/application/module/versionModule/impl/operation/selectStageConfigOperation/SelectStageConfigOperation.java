package com.omgservers.application.module.versionModule.impl.operation.selectStageConfigOperation;

import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectStageConfigOperation {
    Uni<VersionStageConfigModel> selectStageConfig(SqlConnection sqlConnection,
                                                   int shard,
                                                   UUID version);

    default VersionStageConfigModel selectStageConfig(long timeout, PgPool pgPool, int shard, UUID version) {
        return pgPool.withTransaction(sqlConnection -> selectStageConfig(sqlConnection, shard, version))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
