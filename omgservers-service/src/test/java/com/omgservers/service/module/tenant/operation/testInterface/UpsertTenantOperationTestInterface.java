package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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

    public ChangeContext<Boolean> upsertTenant(final int shard,
                                               final TenantModel tenant) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantOperation
                                    .upsertTenant(changeContext, sqlConnection, shard, tenant))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
