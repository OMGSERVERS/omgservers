package com.omgservers.service.module.tenant.impl.operation.deleteTenant;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteTenantOperation {
    Uni<Boolean> deleteTenant(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long id);

    default ChangeContext<Boolean> deleteTenant(long timeout, PgPool pgPool, int shard, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                                    deleteTenant(changeContext, sqlConnection, shard, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
