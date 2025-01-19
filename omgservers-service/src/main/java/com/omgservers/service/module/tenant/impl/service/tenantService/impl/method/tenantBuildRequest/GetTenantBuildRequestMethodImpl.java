package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest.SelectTenantBuildRequestOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantBuildRequestMethodImpl implements GetTenantBuildRequestMethod {

    final SelectTenantBuildRequestOperation selectTenantBuildRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantBuildRequestResponse> execute(
            final GetTenantBuildRequestRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantBuildRequestOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantBuildRequestResponse::new);
    }
}
