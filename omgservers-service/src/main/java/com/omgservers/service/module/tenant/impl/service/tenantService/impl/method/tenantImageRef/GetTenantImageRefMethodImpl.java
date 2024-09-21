package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.SelectTenantImageRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantImageRefMethodImpl implements GetTenantImageRefMethod {

    final SelectTenantImageRefOperation selectTenantImageRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantImageRefResponse> execute(final GetTenantImageRefRequest request) {
        log.debug("Get tenant image ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantImageRefOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantImageRefResponse::new);
    }
}
