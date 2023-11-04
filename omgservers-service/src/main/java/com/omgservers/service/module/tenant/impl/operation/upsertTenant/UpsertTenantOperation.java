package com.omgservers.service.module.tenant.impl.operation.upsertTenant;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertTenantOperation {
    Uni<Boolean> upsertTenant(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              TenantModel tenant);

    default Boolean upsertTenant(long timeout,
                                 PgPool pgPool,
                                 int shard,
                                 TenantModel tenant) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertTenant(changeContext, sqlConnection, shard, tenant));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
