package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.dto.TenantDeploymentDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.SelectTenantDeploymentOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.SelectActiveTenantLobbyRefsByTenantDeploymentIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.SelectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantDeploymentDataMethodImpl implements GetTenantDeploymentDataMethod {

    final SelectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation
            selectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation;
    final SelectActiveTenantLobbyRefsByTenantDeploymentIdOperation
            selectActiveTenantLobbyRefsByTenantDeploymentIdOperation;
    final SelectTenantDeploymentOperation
            selectTenantDeploymentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDeploymentDataResponse> execute(final GetTenantDeploymentDataRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    final var tenantDeploymentData = new TenantDeploymentDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, tenantDeploymentId, tenantDeploymentData));
                })
                .map(GetTenantDeploymentDataResponse::new);
    }

    Uni<TenantDeploymentDataDto> fillData(final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long tenantId,
                                          final Long tenantDeploymentId,
                                          final TenantDeploymentDataDto tenantDeploymentData) {
        return fillTenantDeployment(sqlConnection, shard, tenantId, tenantDeploymentId, tenantDeploymentData)
                .flatMap(voidItem -> fillTenantDeployment(sqlConnection, shard, tenantId, tenantDeploymentId,
                        tenantDeploymentData))
                .flatMap(voidItem -> fillTenantLobbyRef(sqlConnection, shard, tenantId, tenantDeploymentId,
                        tenantDeploymentData))
                .flatMap(voidItem -> fillTenantMatchmakerRef(sqlConnection, shard, tenantId, tenantDeploymentId,
                        tenantDeploymentData))
                .replaceWith(tenantDeploymentData);
    }

    Uni<Void> fillTenantDeployment(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long tenantId,
                                   final Long tenantDeploymentId,
                                   final TenantDeploymentDataDto tenantDeploymentData) {
        return selectTenantDeploymentOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantDeploymentId)
                .invoke(tenantDeploymentData::setTenantDeployment)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantLobbyRef(final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long tenantId,
                                 final Long tenantDeploymentId,
                                 final TenantDeploymentDataDto tenantDeploymentData) {
        return selectActiveTenantLobbyRefsByTenantDeploymentIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantDeploymentId)
                .invoke(tenantDeploymentData::setTenantLobbyRefs)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantMatchmakerRef(final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long tenantId,
                                      final Long tenantDeploymentId,
                                      final TenantDeploymentDataDto tenantDeploymentData) {
        return selectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantDeploymentId)
                .invoke(tenantDeploymentData::setTenantMatchmakerRefs)
                .replaceWithVoid();
    }
}
