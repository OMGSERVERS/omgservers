package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.findVersionMatchmakerRequest;

import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.selectVersionMatchmakerRequestByMatchmakerId.SelectVersionMatchmakerRequestByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionMatchmakerRequestMethodImpl implements FindVersionMatchmakerRequestMethod {

    final SelectVersionMatchmakerRequestByMatchmakerIdOperation selectVersionMatchmakerRequestByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(
            final FindVersionMatchmakerRequestRequest request) {
        log.debug("Find version matchmaker request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakerRequestByMatchmakerIdOperation
                            .selectVersionMatchmakerRequestByMatchmakerId(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    matchmakerId));
                })
                .map(FindVersionMatchmakerRequestResponse::new);
    }
}
