package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantVersionIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest.SelectActiveTenantBuildRequestsByTenantVersionIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectTenantVersionOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantVersionDataMethodImpl implements GetTenantVersionDataMethod {

    final SelectActiveTenantBuildRequestsByTenantVersionIdOperation
            selectActiveTenantBuildRequestsByTenantVersionIdOperation;
    final SelectTenantVersionOperation selectTenantVersionOperation;
    final SelectActiveTenantImageByTenantVersionIdOperation
            selectActiveTenantImageByTenantVersionIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionDataResponse> execute(final GetTenantVersionDataRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantVersionId = request.getTenantVersionId();
                    final var tenantVersionData = new TenantVersionDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, tenantVersionId, tenantVersionData));
                })
                .map(GetTenantVersionDataResponse::new);
    }

    Uni<TenantVersionDataDto> fillData(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tenantId,
                                       final Long tenantVersionId,
                                       final TenantVersionDataDto tenantVersionData) {
        return fillTenantVersion(sqlConnection, shard, tenantId, tenantVersionId, tenantVersionData)
                .flatMap(voidItem -> fillTenantBuildRequests(sqlConnection,
                        shard,
                        tenantId,
                        tenantVersionId,
                        tenantVersionData))
                .flatMap(voidItem -> fillTenantImage(sqlConnection,
                        shard,
                        tenantId,
                        tenantVersionId,
                        tenantVersionData))
                .replaceWith(tenantVersionData);
    }

    Uni<Void> fillTenantVersion(final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final Long tenantVersionId,
                                final TenantVersionDataDto tenantVersionData) {
        return selectTenantVersionOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantVersionId)
                .invoke(tenantVersionData::setTenantVersion)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantBuildRequests(final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long tenantId,
                                      final Long tenantVersionId,
                                      final TenantVersionDataDto tenantVersionData) {
        return selectActiveTenantBuildRequestsByTenantVersionIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantVersionId)
                .invoke(tenantVersionData::setTenantBuildRequests)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantImage(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long tenantVersionId,
                              final TenantVersionDataDto tenantVersionData) {
        return selectActiveTenantImageByTenantVersionIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantVersionId)
                .invoke(tenantVersionData::setTenantImages)
                .replaceWithVoid();
    }
}
