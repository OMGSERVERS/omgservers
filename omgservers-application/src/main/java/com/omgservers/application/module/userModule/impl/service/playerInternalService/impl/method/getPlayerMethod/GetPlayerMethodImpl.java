package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.getPlayerMethod;

import com.omgservers.application.module.userModule.impl.operation.selectPlayerOperation.SelectPlayerOperation;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.userModule.GetPlayerRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPlayerMethodImpl implements GetPlayerMethod {

    final CheckShardOperation checkShardOperation;
    final SelectPlayerOperation selectPlayerOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerInternalResponse> getPlayer(final GetPlayerRoutedRequest request) {
        GetPlayerRoutedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var user = request.getUserId();
                    final var stage = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerOperation
                            .selectPlayer(sqlConnection, shard.shard(), user, stage));
                })
                .map(GetPlayerInternalResponse::new);
    }
}
