package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.getVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectVersionMatchmakerRef.SelectVersionMatchmakerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionMatchmakerRefMethodImpl implements GetVersionMatchmakerRefMethod {

    final SelectVersionMatchmakerRefOperation selectVersionMatchmakerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(final GetVersionMatchmakerRefRequest request) {
        log.debug("Get version matchmaker ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakerRefOperation
                            .selectVersionMatchmakerRef(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionMatchmakerRefResponse::new);
    }
}
