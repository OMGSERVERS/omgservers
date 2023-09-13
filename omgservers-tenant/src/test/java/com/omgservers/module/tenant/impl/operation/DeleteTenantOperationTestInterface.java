package com.omgservers.module.tenant.impl.operation;

import com.omgservers.module.tenant.impl.operation.deleteTenant.DeleteTenantOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
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
                                    .deleteTenant(changeContext, sqlConnection, shard, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .invoke(changeContext -> log.info("Change context, {}", changeContext))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}