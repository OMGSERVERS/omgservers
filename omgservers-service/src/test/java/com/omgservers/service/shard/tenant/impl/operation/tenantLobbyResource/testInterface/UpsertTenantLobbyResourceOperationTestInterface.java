package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.testInterface;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.UpsertTenantLobbyResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertTenantLobbyResourceOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantLobbyResourceOperation upsertTenantLobbyResourceOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantLobbyResourceModel tenantLobbyResource) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantLobbyResourceOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            0,
                                            tenantLobbyResource))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
