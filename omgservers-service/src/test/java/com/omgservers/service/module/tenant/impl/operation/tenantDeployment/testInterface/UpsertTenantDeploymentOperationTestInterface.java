package com.omgservers.service.module.tenant.impl.operation.tenantDeployment.testInterface;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.UpsertTenantDeploymentOperation;
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
public class UpsertTenantDeploymentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantDeploymentOperation upsertTenantDeploymentOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantDeploymentModel tenantDeployment) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantDeploymentOperation
                                    .execute(changeContext, sqlConnection, 0, tenantDeployment))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
