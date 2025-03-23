package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.FindTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.FindTenantMatchmakerResourceResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.SelectTenantMatchmakerResourceByMatchmakerIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantMatchmakerResourceMethodImpl implements FindTenantMatchmakerResourceMethod {

    final SelectTenantMatchmakerResourceByMatchmakerIdOperation selectTenantMatchmakerResourceByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantMatchmakerResourceResponse> execute(
            final FindTenantMatchmakerResourceRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getDeploymentId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantMatchmakerResourceByMatchmakerIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    tenantDeploymentId,
                                    matchmakerId));
                })
                .map(FindTenantMatchmakerResourceResponse::new);
    }
}
