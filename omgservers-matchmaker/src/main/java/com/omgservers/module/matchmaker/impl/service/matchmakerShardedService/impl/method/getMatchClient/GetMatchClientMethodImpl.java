package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.getMatchClient;

import com.omgservers.dto.matchmaker.GetMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchClientShardedResponse;
import com.omgservers.module.matchmaker.impl.operation.selectMatchClient.SelectMatchClientOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchClientMethodImpl implements GetMatchClientMethod {

    final SelectMatchClientOperation selectMatchClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchClientShardedResponse> getMatchClient(GetMatchClientShardedRequest request) {
        GetMatchClientShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchClientOperation
                            .selectMatchClient(sqlConnection, shard.shard(), id));
                })
                .map(GetMatchClientShardedResponse::new);
    }
}
