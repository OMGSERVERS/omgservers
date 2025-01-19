package com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest.testInterface;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest.UpsertTenantLobbyRequestOperation;
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
public class UpsertTenantLobbyRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantLobbyRequestOperation upsertTenantLobbyRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantLobbyRequestModel tenantLobbyRequest) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantLobbyRequestOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            0,
                                            tenantLobbyRequest))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
