package com.omgservers.service.operation.server;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.ShardedRequest;
import com.omgservers.service.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor
class HandleShardedRequestOperationImpl implements HandleShardedRequestOperation {

    final CalculateShardOperation calculateShardOperation;
    final PutIntoMdcOperation putIntoMdcOperation;

    @Override
    public <T extends ShardedRequest, R, C> Uni<R> handleShardedRequest(final Logger log,
                                                                        final T request,
                                                                        final Function<URI, C> api,
                                                                        final BiFunction<C, T, Uni<R>> route,
                                                                        final BiFunction<ShardModel, T, Uni<R>> handle) {
        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    putIntoMdcOperation.putShard(shardModel.shard());

                    if (shardModel.locked()) {
                        throw new ServerSideInternalException(ExceptionQualifierEnum.SHARD_LOCKED,
                                "shardModel is locked, shardModel=" + shardModel.shard());
                    }

                    final Uni<R> operation;
                    final var serverUri = shardModel.serverUri();
                    if (shardModel.foreign()) {
                        final var client = api.apply(serverUri);
                        log.trace("Route request, targetServer={}, request={}", serverUri, request);
                        operation = route.apply(client, request);
                    } else {
                        log.trace("Handle request, request={}", request);
                        operation = handle.apply(shardModel, request);
                    }

                    return operation
                            .onFailure()
                            .invoke(t -> {
                                if (t instanceof ServerSideInternalException) {
                                    log.warn("Sharded request failed, uri={}, request={}, {}:{}",
                                            serverUri, request, t.getClass().getSimpleName(), t.getMessage());
                                } else {
                                    log.debug("Sharded request failed, uri={}, request={}, {}:{}",
                                            serverUri, request, t.getClass().getSimpleName(), t.getMessage());
                                }
                            });
                });
    }
}
