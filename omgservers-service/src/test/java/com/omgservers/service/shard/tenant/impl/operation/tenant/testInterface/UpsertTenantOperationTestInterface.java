package com.omgservers.service.shard.tenant.impl.operation.tenant.testInterface;

import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.shard.tenant.impl.operation.tenant.UpsertTenantOperation;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertTenantOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantOperation upsertTenantOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertTenant(final TenantModel tenant) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantOperation
                                    .execute(changeContext, sqlConnection, 0, tenant))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
