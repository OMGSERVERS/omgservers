package com.omgservers.service.operation.handleInternalRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.jboss.logmanager.MDC;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor
class HandleShardedRequestOperationImpl implements HandleShardedRequestOperation {

    final CalculateShardOperation calculateShardOperation;

    @Override
    public <T extends ShardedRequest, R, C> Uni<R> handleShardedRequest(final Logger log,
                                                                        final T request,
                                                                        final Function<URI, C> api,
                                                                        final BiFunction<C, T, Uni<? extends R>> route,
                                                                        final Function<T, Uni<? extends R>> handle) {
        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    MDC.put("shard", String.valueOf(shardModel.shard()));

                    if (shardModel.locked()) {
                        throw new ServerSideInternalException(ExceptionQualifierEnum.SHARD_LOCKED,
                                "shardModel is locked, shardModel=" + shardModel.shard());
                    }

                    if (shardModel.foreign()) {
                        var serverUri = shardModel.serverUri();
                        final var client = api.apply(serverUri);
                        log.trace("Route request, targetServer={}, request={}", serverUri, request);
                        return route.apply(client, request);
                    } else {
                        log.trace("Handle request, request={}", request);
                        return handle.apply(request);
                    }
                })
                .onFailure()
                .invoke(t -> {
                    if (!(t instanceof ServerSideNotFoundException)) {
                        log.warn("Internal request failed, request={}, {}:{}",
                                request, t.getClass().getSimpleName(), t.getMessage());
                    }
                });
    }
}
