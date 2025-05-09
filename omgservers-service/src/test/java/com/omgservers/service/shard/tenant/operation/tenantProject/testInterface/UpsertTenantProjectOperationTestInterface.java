package com.omgservers.service.shard.tenant.operation.tenantProject.testInterface;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.UpsertTenantProjectOperation;
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
public class UpsertTenantProjectOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantProjectOperation upsertTenantProjectOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantProjectModel project) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantProjectOperation
                                    .execute(changeContext, sqlConnection, 0, project))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
