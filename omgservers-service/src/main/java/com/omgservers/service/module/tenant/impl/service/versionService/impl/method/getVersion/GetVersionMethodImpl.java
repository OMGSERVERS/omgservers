package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersion;

import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.service.module.tenant.impl.operation.version.selectVersion.SelectVersionOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionMethodImpl implements GetVersionMethod {

    final SelectVersionOperation selectVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionResponse> getVersion(GetVersionRequest request) {
        log.debug("Get version, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionOperation
                            .selectVersion(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionResponse::new);
    }
}
