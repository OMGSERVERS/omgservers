package com.omgservers.service.module.tenant.impl.operation.tenantImage.testInterface;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.SelectTenantImageByTenantVersionIdAndQualifierOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantImageByTenantVersionIdAndQualifierOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantImageByTenantVersionIdAndQualifierOperation
            selectTenantImageByTenantVersionIdAndQualifierOperation;

    final PgPool pgPool;

    public TenantImageModel execute(final Long tenantId,
                                    final Long tenantVersionId,
                                    final TenantImageQualifierEnum qualifier) {
        return pgPool.withTransaction(sqlConnection -> selectTenantImageByTenantVersionIdAndQualifierOperation
                        .execute(sqlConnection, 0, tenantId, tenantVersionId, qualifier))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
