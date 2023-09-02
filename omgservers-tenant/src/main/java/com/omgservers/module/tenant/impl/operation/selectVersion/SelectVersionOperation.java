package com.omgservers.module.tenant.impl.operation.selectVersion;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectVersionOperation {
    Uni<VersionModel> selectVersion(SqlConnection sqlConnection,
                                    int shard,
                                    Long id);

    default VersionModel selectVersion(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectVersion(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
