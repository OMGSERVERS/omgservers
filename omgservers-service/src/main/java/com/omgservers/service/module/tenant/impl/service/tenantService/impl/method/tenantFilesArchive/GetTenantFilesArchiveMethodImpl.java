package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.SelectTenantFilesArchiveOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantFilesArchiveMethodImpl implements GetTenantFilesArchiveMethod {

    final SelectTenantFilesArchiveOperation selectTenantFilesArchiveOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantFilesArchiveResponse> execute(
            final GetTenantFilesArchiveRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantFilesArchiveOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantFilesArchiveResponse::new);
    }
}
