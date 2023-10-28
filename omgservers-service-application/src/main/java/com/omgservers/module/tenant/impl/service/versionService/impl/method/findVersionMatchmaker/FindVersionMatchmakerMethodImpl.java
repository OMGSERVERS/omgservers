package com.omgservers.module.tenant.impl.service.versionService.impl.method.findVersionMatchmaker;

import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.module.tenant.impl.operation.selectVersionMatchmakerByMatchmakerId.SelectVersionMatchmakerByMatchmakerIdOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionMatchmakerMethodImpl implements FindVersionMatchmakerMethod {

    final SelectVersionMatchmakerByMatchmakerIdOperation selectVersionMatchmakerByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(final FindVersionMatchmakerRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakerByMatchmakerIdOperation
                            .selectVersionMatchmakerByMatchmakerId(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    matchmakerId));
                })
                .map(FindVersionMatchmakerResponse::new);
    }
}
