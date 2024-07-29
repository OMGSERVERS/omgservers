package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.findVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectVersionMatchmakerRefByMatchmakerId.SelectVersionMatchmakerRefByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionMatchmakerRefMethodImpl implements FindVersionMatchmakerRefMethod {

    final SelectVersionMatchmakerRefByMatchmakerIdOperation selectVersionMatchmakerRefByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(
            final FindVersionMatchmakerRefRequest request) {
        log.debug("Find version matchmaker ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakerRefByMatchmakerIdOperation
                            .selectVersionMatchmakerRefByMatchmakerId(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    matchmakerId));
                })
                .map(FindVersionMatchmakerRefResponse::new);
    }
}
