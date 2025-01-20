package com.omgservers.service.shard.tenant.impl.operation.tenantVersion.testInterface;

import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.DeleteTenantVersionOperation;
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
public class DeleteTenantVersionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteTenantVersionOperation deleteTenantVersionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final Long tenantId,
                                          final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteTenantVersionOperation
                                    .execute(changeContext, sqlConnection, 0, tenantId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
