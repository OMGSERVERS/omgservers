package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionRuntime;

import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.service.module.tenant.impl.operation.selectVersionRuntimeByRuntimeId.SelectVersionRuntimeByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionRuntimeMethodImpl implements FindVersionRuntimeMethod {

    final SelectVersionRuntimeByRuntimeIdOperation selectVersionRuntimeByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionRuntimeResponse> findVersionRuntime(final FindVersionRuntimeRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionRuntimeByRuntimeIdOperation
                            .selectVersionRuntimeByRuntimeId(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    runtimeId));
                })
                .map(FindVersionRuntimeResponse::new);
    }
}
