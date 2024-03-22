package com.omgservers.service.operation.handleInternalRequest;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor
class HandleInternalRequestOperationImpl implements HandleInternalRequestOperation {

    final CalculateShardOperation calculateShardOperation;

    @Override
    public <T extends ShardedRequest, R, C> Uni<R> handleInternalRequest(final Logger log,
                                                                         final T request,
                                                                         final Function<URI, C> api,
                                                                         final BiFunction<C, T, Uni<? extends R>> route,
                                                                         final Function<T, Uni<? extends R>> handle) {
        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    if (shard.locked()) {
                        throw new ServerSideInternalException(ExceptionQualifierEnum.SHARD_LOCKED,
                                "shard is locked, shard=" + shard.shard());

                    }

                    if (shard.foreign()) {
                        var serverUri = shard.serverUri();
                        final var client = api.apply(serverUri);
                        log.debug("Route request, targetServer={}, request={}", serverUri, request);
                        return route.apply(client, request);
                    } else {
                        log.debug("Handle request, request={}", request);
                        return handle.apply(request);
                    }
                });
    }
}
