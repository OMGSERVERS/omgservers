package com.omgservers.service.module.tenant.impl.operation.selectTenant;

import com.omgservers.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectTenantOperation {
    Uni<TenantModel> selectTenant(SqlConnection sqlConnection, int shard, Long id);

    default TenantModel selectTenant(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectTenant(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
