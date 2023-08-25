package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.getClientMethod;

import com.omgservers.application.module.userModule.impl.operation.selectClientOperation.SelectClientOperation;
import com.omgservers.base.impl.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.dto.userModule.GetClientInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.exception.ServerSideGoneException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientMethodImpl implements GetClientMethod {

    final CalculateShardOperation calculateShardOperation;
    final SelectClientOperation selectClientOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetClientInternalResponse> getClient(final GetClientInternalRequest request) {
        GetClientInternalRequest.validate(request);

        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    if (shardModel.foreign()) {
                        throw new ServerSideGoneException("wrong shard server, shard=" + shardModel.shard());
                    }

                    final var client = request.getClientId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection -> selectClientOperation
                            .selectClient(sqlConnection, shard, client));
                })
                .map(GetClientInternalResponse::new);
    }
}
