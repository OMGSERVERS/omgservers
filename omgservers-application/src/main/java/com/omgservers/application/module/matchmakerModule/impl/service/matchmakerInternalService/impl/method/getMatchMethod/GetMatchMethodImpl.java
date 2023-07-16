package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchOperation.SelectMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.GetMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.GetMatchInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchMethodImpl implements GetMatchMethod {

    final SelectMatchOperation selectMatchOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request) {
        GetMatchInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var uuid = request.getUuid();
                    return pgPool.withTransaction(sqlConnection -> selectMatchOperation
                            .selectMatch(sqlConnection, shard.shard(), uuid));
                })
                .map(GetMatchInternalResponse::new);
    }
}
