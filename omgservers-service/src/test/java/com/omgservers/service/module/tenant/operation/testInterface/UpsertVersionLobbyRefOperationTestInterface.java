package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.UpsertTenantLobbyRefOperation;
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
public class UpsertVersionLobbyRefOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantLobbyRefOperation upsertTenantLobbyRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionLobbyRef(final int shard,
                                                        final TenantLobbyRefModel versionLobbyRef) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantLobbyRefOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            shard,
                                            versionLobbyRef))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
