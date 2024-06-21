package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.viewMatchmakerRequests;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.selectActiveMatchmakerRequestsByMatchmakerId.SelectActiveMatchmakerRequestsByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerRequestsMethodImpl implements ViewMatchmakerRequestsMethod {

    final SelectActiveMatchmakerRequestsByMatchmakerIdOperation selectActiveMatchmakerRequestsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(ViewMatchmakerRequestsRequest request) {
        log.debug("View requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerRequestsByMatchmakerIdOperation
                            .selectActiveMatchmakerRequestsByMatchmakerId(sqlConnection, shard.shard(), matchmakerId));
                })
                .map(ViewMatchmakerRequestsResponse::new);

    }
}
