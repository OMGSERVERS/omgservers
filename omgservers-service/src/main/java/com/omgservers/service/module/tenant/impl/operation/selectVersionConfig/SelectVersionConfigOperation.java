package com.omgservers.service.module.tenant.impl.operation.selectVersionConfig;

import com.omgservers.model.version.VersionConfigModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectVersionConfigOperation {
    Uni<VersionConfigModel> selectVersionConfig(SqlConnection sqlConnection,
                                                int shard,
                                                Long tenantId,
                                                Long versionId);

    default VersionConfigModel selectVersionConfig(long timeout,
                                                   PgPool pgPool,
                                                   int shard,
                                                   Long tenantId,
                                                   Long versionId) {
        return pgPool.withTransaction(sqlConnection -> selectVersionConfig(sqlConnection, shard, tenantId, versionId))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
