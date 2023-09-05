package com.omgservers.module.tenant.impl.operation.upsertVersion;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertVersionOperation {
    Uni<Boolean> upsertVersion(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long tenantId,
                               VersionModel version);

    default Boolean upsertVersion(long timeout,
                                  PgPool pgPool,
                                  int shard,
                                  Long tenantId,
                                  VersionModel version) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertVersion(changeContext, sqlConnection, shard, tenantId, version));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
