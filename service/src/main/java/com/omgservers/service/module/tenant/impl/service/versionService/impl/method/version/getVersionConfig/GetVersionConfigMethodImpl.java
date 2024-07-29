package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersionConfig;

import com.omgservers.schema.module.tenant.GetVersionConfigRequest;
import com.omgservers.schema.module.tenant.GetVersionConfigResponse;
import com.omgservers.service.module.tenant.impl.operation.version.selectVersionConfig.SelectVersionConfigOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionConfigMethodImpl implements GetVersionConfigMethod {

    final SelectVersionConfigOperation selectVersionConfigOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request) {
        log.debug("Get version config, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionConfigOperation
                            .selectVersionConfig(sqlConnection, shard, tenantId, versionId));
                })
                .map(GetVersionConfigResponse::new);
    }
}
