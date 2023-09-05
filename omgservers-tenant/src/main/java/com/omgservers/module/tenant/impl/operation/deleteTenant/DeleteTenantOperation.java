package com.omgservers.module.tenant.impl.operation.deleteTenant;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteTenantOperation {
    Uni<Boolean> deleteTenant(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long id);

    default Boolean deleteTenant(long timeout, PgPool pgPool, int shard, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteTenant(changeContext, sqlConnection, shard, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
