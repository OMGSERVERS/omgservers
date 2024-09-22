package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.service.module.tenant.impl.operation.tenant.DeleteTenantOperation;
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
public class DeleteTenantOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteTenantOperation deleteTenantOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteTenant(final int shard, final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteTenantOperation
                                    .execute(changeContext, sqlConnection, shard, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
