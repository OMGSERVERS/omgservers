package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmaker;

import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.module.matchmaker.impl.operation.selectMatchmaker.SelectMatchmakerOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMethodImpl implements GetMatchmakerMethod {

    final SelectMatchmakerOperation selectMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request) {
        GetMatchmakerRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                            .selectMatchmaker(sqlConnection, shard.shard(), id));
                })
                .map(GetMatchmakerResponse::new);
    }
}
