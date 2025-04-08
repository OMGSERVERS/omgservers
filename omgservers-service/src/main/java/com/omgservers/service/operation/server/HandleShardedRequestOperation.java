package com.omgservers.service.operation.server;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.ShardedRequest;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface HandleShardedRequestOperation {
    <T extends ShardedRequest, R, C> Uni<R> handleShardedRequest(Logger log,
                                                                 T request,
                                                                 Function<URI, C> api,
                                                                 BiFunction<C, T, Uni<? extends R>> route,
                                                                 BiFunction<ShardModel, T, Uni<? extends R>> handle);
}
