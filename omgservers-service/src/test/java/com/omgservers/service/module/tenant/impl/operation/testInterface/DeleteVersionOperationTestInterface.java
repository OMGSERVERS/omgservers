package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.service.module.tenant.impl.operation.tenantVersion.DeleteTenantVersionOperation;
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
public class DeleteVersionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteTenantVersionOperation deleteTenantVersionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteVersion(final int shard,
                                                final Long tenantId,
                                                final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteTenantVersionOperation
                                    .execute(changeContext, sqlConnection, shard, tenantId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
