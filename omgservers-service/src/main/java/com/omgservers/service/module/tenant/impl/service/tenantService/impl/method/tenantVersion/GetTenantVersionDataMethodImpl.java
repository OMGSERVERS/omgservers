package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectTenantVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.SelectActiveTenantImageRefsByTenantVersionIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.SelectActiveTenantLobbyRefsByTenantDeploymentIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.SelectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation;
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

    final SelectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation
            selectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation;
    final SelectActiveTenantLobbyRefsByTenantDeploymentIdOperation
            selectActiveTenantLobbyRefsByTenantDeploymentIdOperation;
    final SelectActiveTenantImageRefsByTenantVersionIdOperation selectActiveTenantImageRefsByTenantVersionIdOperation;
    final SelectTenantVersionOperation selectTenantVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionDataResponse> execute(final GetTenantVersionDataRequest request) {
        log.debug("Get version data, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getTenantVersionId();
                    final var versionData = new TenantVersionDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, versionId, versionData));
                })
                .map(GetTenantVersionDataResponse::new);
    }

    Uni<TenantVersionDataDto> fillData(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tenantId,
                                       final Long versionId,
                                       final TenantVersionDataDto versionData) {
        return selectVersion(sqlConnection, shard, tenantId, versionId, versionData)
                .flatMap(voidItem -> selectImageRefs(sqlConnection, shard, tenantId, versionId, versionData))
                .flatMap(voidItem -> selectLobbyRefs(sqlConnection, shard, tenantId, versionId, versionData))
                .flatMap(voidItem -> selectMatchmakerRefs(sqlConnection, shard, tenantId, versionId, versionData))
                .replaceWith(versionData);
    }

    Uni<Void> selectVersion(final SqlConnection sqlConnection,
                            final int shard,
                            final Long tenantId,
                            final Long versionId,
                            final TenantVersionDataDto versionData) {
        return selectTenantVersionOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setTenantVersion)
                .replaceWithVoid();
    }

    Uni<Void> selectImageRefs(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long versionId,
                              final TenantVersionDataDto versionData) {
        return selectActiveTenantImageRefsByTenantVersionIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setTenantImageRefs)
                .replaceWithVoid();
    }

    Uni<Void> selectLobbyRefs(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long versionId,
                              final TenantVersionDataDto versionData) {
        return selectActiveTenantLobbyRefsByTenantDeploymentIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setTenantLobbyRefs)
                .replaceWithVoid();
    }

    Uni<Void> selectMatchmakerRefs(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long tenantId,
                                   final Long versionId,
                                   final TenantVersionDataDto versionData) {
        return selectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        versionId)
                .invoke(versionData::setTenantMatchmakerRefs)
                .replaceWithVoid();
    }
}
