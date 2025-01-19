package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.service.operation.server.CalculateShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CalculateShardMethodImpl implements CalculateShardMethod {

    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<CalculateShardDispatcherResponse> execute(final CalculateShardDispatcherRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getShardKey();

        return calculateShardOperation.calculateShard(shardKey)
                .map(shardModel -> new CalculateShardDispatcherResponse(shardModel.shard(),
                        shardModel.serverUri(), shardModel.foreign()));
    }
}
