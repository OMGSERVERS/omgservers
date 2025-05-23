package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantVersionIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.SelectTenantVersionOperation;
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

    final SelectTenantVersionOperation selectTenantVersionOperation;
    final SelectActiveTenantImageByTenantVersionIdOperation
            selectActiveTenantImageByTenantVersionIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionDataResponse> execute(final ShardModel shardModel,
                                                     final GetTenantVersionDataRequest request) {
        log.debug("{}", request);

        final int slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var tenantVersionId = request.getTenantVersionId();
        final var tenantVersionData = new TenantVersionDataDto();

        return pgPool.withTransaction(sqlConnection ->
                        fillData(sqlConnection, slot, tenantId, tenantVersionId, tenantVersionData))
                .map(GetTenantVersionDataResponse::new);
    }

    Uni<TenantVersionDataDto> fillData(final SqlConnection sqlConnection,
                                       final int slot,
                                       final Long tenantId,
                                       final Long tenantVersionId,
                                       final TenantVersionDataDto tenantVersionData) {
        return fillTenantVersion(sqlConnection, slot, tenantId, tenantVersionId, tenantVersionData)
                .flatMap(voidItem -> fillTenantImage(sqlConnection,
                        slot,
                        tenantId,
                        tenantVersionId,
                        tenantVersionData))
                .replaceWith(tenantVersionData);
    }

    Uni<Void> fillTenantVersion(final SqlConnection sqlConnection,
                                final int slot,
                                final Long tenantId,
                                final Long tenantVersionId,
                                final TenantVersionDataDto tenantVersionData) {
        return selectTenantVersionOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantVersionId)
                .invoke(tenantVersionData::setTenantVersion)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantImage(final SqlConnection sqlConnection,
                              final int slot,
                              final Long tenantId,
                              final Long tenantVersionId,
                              final TenantVersionDataDto tenantVersionData) {
        return selectActiveTenantImageByTenantVersionIdOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantVersionId)
                .invoke(tenantVersionData::setTenantImages)
                .replaceWithVoid();
    }
}
