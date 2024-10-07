package com.omgservers.service.module.tenant.impl.operation.tenantImage.testInterface;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.SelectTenantImageOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantImageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantImageOperation selectTenantImageOperation;

    final PgPool pgPool;

    public TenantImageModel execute(final Long tenantId,
                                    final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectTenantImageOperation
                        .execute(sqlConnection, 0, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
