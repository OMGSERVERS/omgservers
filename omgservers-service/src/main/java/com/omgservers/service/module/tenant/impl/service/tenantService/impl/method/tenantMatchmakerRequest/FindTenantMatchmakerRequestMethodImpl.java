package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest.SelectTenantMatchmakerRequestByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantMatchmakerRequestMethodImpl implements FindTenantMatchmakerRequestMethod {

    final SelectTenantMatchmakerRequestByMatchmakerIdOperation selectTenantMatchmakerRequestByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantMatchmakerRequestResponse> execute(
            final FindTenantMatchmakerRequestRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getDeploymentId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantMatchmakerRequestByMatchmakerIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    tenantDeploymentId,
                                    matchmakerId));
                })
                .map(FindTenantMatchmakerRequestResponse::new);
    }
}
