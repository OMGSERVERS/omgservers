package com.omgservers.service.module.tenant.impl.operation.tenantVersion.testInterface;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.UpsertTenantVersionOperation;
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
public class UpsertTenantVersionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantVersionOperation upsertTenantVersionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantVersionModel tenantVersion) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantVersionOperation
                                    .execute(changeContext, sqlConnection, 0, tenantVersion))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
