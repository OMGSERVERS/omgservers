package com.omgservers.module.tenant.impl.operation.deleteVersion;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteVersionOperation {
    Uni<Boolean> deleteVersion(ChangeContext changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long tenantId,
                               Long id);

    default Boolean deleteVersion(long timeout,
                                  PgPool pgPool,
                                  int shard,
                                  Long tenantId,
                                  Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteVersion(changeContext, sqlConnection, shard, tenantId, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
