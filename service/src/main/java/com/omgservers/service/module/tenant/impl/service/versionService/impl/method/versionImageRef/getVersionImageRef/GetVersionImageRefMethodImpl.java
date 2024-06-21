package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.getVersionImageRef;

import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectVersionImageRef.SelectVersionImageRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionImageRefMethodImpl implements GetVersionImageRefMethod {

    final SelectVersionImageRefOperation selectVersionImageRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionImageRefResponse> getVersionImageRef(final GetVersionImageRefRequest request) {
        log.debug("Get version image ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionImageRefOperation
                            .selectVersionImageRef(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionImageRefResponse::new);
    }
}
