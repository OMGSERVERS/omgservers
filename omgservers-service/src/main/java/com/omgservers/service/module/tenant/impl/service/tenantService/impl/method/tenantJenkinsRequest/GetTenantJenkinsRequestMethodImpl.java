package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest.SelectTenantJenkinsRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantJenkinsRequestMethodImpl implements GetTenantJenkinsRequestMethod {

    final SelectTenantJenkinsRequestOperation selectTenantJenkinsRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantJenkinsRequestResponse> execute(
            final GetTenantJenkinsRequestRequest request) {
        log.debug("Get tenant jenkins request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantJenkinsRequestOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantJenkinsRequestResponse::new);
    }
}
