package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.SelectTenantMatchmakerRefByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantMatchmakerRefMethodImpl implements FindTenantMatchmakerRefMethod {

    final SelectTenantMatchmakerRefByMatchmakerIdOperation selectTenantMatchmakerRefByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantMatchmakerRefResponse> execute(
            final FindTenantMatchmakerRefRequest request) {
        log.debug("Find tenant matchmaker ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getDeploymentId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantMatchmakerRefByMatchmakerIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    matchmakerId));
                })
                .map(FindTenantMatchmakerRefResponse::new);
    }
}
