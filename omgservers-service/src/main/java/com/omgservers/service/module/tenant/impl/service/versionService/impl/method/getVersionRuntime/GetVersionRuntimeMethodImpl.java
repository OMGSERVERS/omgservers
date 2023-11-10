package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionRuntime;

import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.service.module.tenant.impl.operation.selectVersionRuntime.SelectVersionRuntimeOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionRuntimeMethodImpl implements GetVersionRuntimeMethod {

    final SelectVersionRuntimeOperation selectVersionRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionRuntimeResponse> getVersionRuntime(final GetVersionRuntimeRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionRuntimeOperation
                            .selectVersionRuntime(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionRuntimeResponse::new);
    }
}
