package com.omgservers.base.operation.handleInternalRequest;

import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.dto.RoutedRequest;
import com.omgservers.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor
class HandleInternalRequestOperationImpl implements HandleInternalRequestOperation {

    final CalculateShardOperation calculateShardOperation;

    @Override
    public <T extends RoutedRequest, R, C> Uni<R> handleInternalRequest(final Logger log,
                                                                        final T request,
                                                                        final Consumer<T> validation,
                                                                        final Function<URI, C> api,
                                                                        final BiFunction<C, T, Uni<? extends R>> route,
                                                                        final Function<T, Uni<? extends R>> handle) {
        validation.accept(request);
        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    if (shard.locked()) {
                        throw new ServerSideInternalException("shard is locked, shard=" + shard.shard());
                    }

                    if (shard.foreign()) {
                        var serverUri = shard.serverUri();
                        log.info("Request will be routed, request={}, shard={}", request, shard);
                        final var client = api.apply(serverUri);
                        return route.apply(client, request);
                    } else {
                        return handle.apply(request);
                    }
                });
    }
}
