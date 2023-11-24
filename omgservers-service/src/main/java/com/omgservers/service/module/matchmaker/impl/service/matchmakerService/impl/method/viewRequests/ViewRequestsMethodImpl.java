package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewRequests;

import com.omgservers.model.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveRequestsByMatchmakerId.SelectActiveRequestsByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRequestsMethodImpl implements ViewRequestsMethod {

    final SelectActiveRequestsByMatchmakerIdOperation selectActiveRequestsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRequestsResponse> viewRequests(ViewRequestsRequest request) {
        log.debug("View requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRequestsByMatchmakerIdOperation
                            .selectActiveRequestsByMatchmakerId(sqlConnection, shard.shard(), matchmakerId));
                })
                .map(ViewRequestsResponse::new);

    }
}
