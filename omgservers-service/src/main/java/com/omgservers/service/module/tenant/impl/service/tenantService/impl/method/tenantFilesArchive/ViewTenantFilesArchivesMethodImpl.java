package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.SelectActiveTenantFilesArchiveProjectionsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.SelectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantFilesArchivesMethodImpl implements ViewTenantFilesArchivesMethod {

    final SelectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation
            selectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation;
    final SelectActiveTenantFilesArchiveProjectionsByTenantIdOperation
            selectActiveTenantFilesArchiveProjectionsByTenantIdOperation;

    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantFilesArchivesResponse> execute(
            final ViewTenantFilesArchivesRequest request) {
        log.debug("View tenant files archive, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                        final var tenantVersionId = request.getTenantVersionId();
                        if (Objects.nonNull(tenantVersionId)) {
                            return selectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantVersionId);
                        } else {
                            return selectActiveTenantFilesArchiveProjectionsByTenantIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId);
                        }
                    });
                })
                .map(ViewTenantFilesArchivesResponse::new);
    }
}
