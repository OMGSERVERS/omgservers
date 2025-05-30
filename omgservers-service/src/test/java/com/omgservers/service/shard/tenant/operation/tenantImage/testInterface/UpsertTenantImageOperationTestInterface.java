package com.omgservers.service.shard.tenant.operation.tenantImage.testInterface;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.UpsertTenantImageOperation;
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
public class UpsertTenantImageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantImageOperation upsertTenantImageOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantImageModel tenantImage) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantImageOperation
                                    .execute(changeContext, sqlConnection, 0, tenantImage))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
