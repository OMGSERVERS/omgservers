package com.omgservers.service.shard.tenant.impl.operation.tenantImage.testInterface;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectActiveTenantImagesByTenantIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantImageByTenantIdOperation selectActiveTenantImageByTenantIdOperation;

    final PgPool pgPool;

    public List<TenantImageModel> execute(final Long tenantId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantImageByTenantIdOperation
                        .execute(sqlConnection, 0, tenantId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
