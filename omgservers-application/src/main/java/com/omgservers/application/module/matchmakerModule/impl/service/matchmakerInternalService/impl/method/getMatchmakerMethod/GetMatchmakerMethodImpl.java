package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchmakerMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchmakerOperation.SelectMatchmakerOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.matchmakerModule.GetMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
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
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerShardRequest request) {
        GetMatchmakerShardRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                            .selectMatchmaker(sqlConnection, shard.shard(), id));
                })
                .map(GetMatchmakerInternalResponse::new);
    }
}
