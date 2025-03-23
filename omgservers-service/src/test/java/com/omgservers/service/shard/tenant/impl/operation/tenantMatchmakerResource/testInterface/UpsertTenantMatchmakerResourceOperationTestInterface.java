package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.testInterface;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.UpsertTenantMatchmakerResourceOperation;
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
public class UpsertTenantMatchmakerResourceOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantMatchmakerResourceOperation upsertTenantMatchmakerResourceOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantMatchmakerResourceModel tenantMatchmakerResource) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantMatchmakerResourceOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            0,
                                            tenantMatchmakerResource))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
