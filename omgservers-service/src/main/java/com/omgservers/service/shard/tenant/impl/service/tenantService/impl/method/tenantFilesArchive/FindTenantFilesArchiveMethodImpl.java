package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.SelectTenantFilesArchiveByTenantVersionIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantFilesArchiveMethodImpl implements FindTenantFilesArchiveMethod {

    final SelectTenantFilesArchiveByTenantVersionIdOperation selectTenantFilesArchiveByTenantVersionIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindTenantFilesArchiveResponse> execute(
            final FindTenantFilesArchiveRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantVersionId = request.getTenantVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantFilesArchiveByTenantVersionIdOperation
                            .execute(sqlConnection, shard, tenantId, tenantVersionId));
                })
                .map(FindTenantFilesArchiveResponse::new);
    }
}
