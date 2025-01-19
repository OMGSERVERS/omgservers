package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.SelectTenantMatchmakerRefOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantMatchmakerRefMethodImpl implements GetTenantMatchmakerRefMethod {

    final SelectTenantMatchmakerRefOperation selectTenantMatchmakerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantMatchmakerRefResponse> execute(final GetTenantMatchmakerRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantMatchmakerRefOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantMatchmakerRefResponse::new);
    }
}
