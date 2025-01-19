package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.testInterface;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.UpsertTenantFilesArchiveOperation;
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
public class UpsertTenantFilesArchiveOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantFilesArchiveOperation upsertTenantFilesArchiveOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantFilesArchiveModel tenantFilesArchive) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantFilesArchiveOperation
                                    .execute(changeContext, sqlConnection, 0, tenantFilesArchive))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
