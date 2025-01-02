package com.omgservers.dispatcher.service.service.impl.method;

import com.omgservers.dispatcher.operation.getDispatcherConfig.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.operation.getServiceDispatcherEntrypointClient.GetServiceDispatcherEntrypointClientOperation;
import com.omgservers.dispatcher.service.service.dto.CalculateShardRequest;
import com.omgservers.dispatcher.service.service.dto.CalculateShardResponse;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CalculateShardMethodImpl implements CalculateShardMethod {

    final GetServiceDispatcherEntrypointClientOperation getServiceDispatcherEntrypointClientOperation;
    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    @Override
    public Uni<CalculateShardResponse> execute(final CalculateShardRequest request) {
        final var serviceUri = getDispatcherConfigOperation.getDispatcherConfig().serviceUri();
        final var serviceClient = getServiceDispatcherEntrypointClientOperation
                .getClient(serviceUri);

        final var shardKey = request.getShardKey();

        final var calculateShardDispatcherRequest = new CalculateShardDispatcherRequest(shardKey);
        return serviceClient.execute(calculateShardDispatcherRequest)
                .map(response -> new CalculateShardResponse(response.getServerUri(), response.getForeign()));
    }
}
