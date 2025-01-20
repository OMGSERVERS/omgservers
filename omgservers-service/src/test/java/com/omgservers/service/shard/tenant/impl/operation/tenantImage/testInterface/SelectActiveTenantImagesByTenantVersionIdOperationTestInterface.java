package com.omgservers.service.shard.tenant.impl.operation.tenantImage.testInterface;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantVersionIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectActiveTenantImagesByTenantVersionIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantImageByTenantVersionIdOperation
            selectActiveTenantImageByTenantVersionIdOperation;

    final PgPool pgPool;

    public List<TenantImageModel> execute(final Long tenantId,
                                          final Long tenantVersionId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantImageByTenantVersionIdOperation
                        .execute(sqlConnection, 0, tenantId, tenantVersionId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
