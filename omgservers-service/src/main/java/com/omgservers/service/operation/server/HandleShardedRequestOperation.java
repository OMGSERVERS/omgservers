package com.omgservers.service.operation.server;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.ShardRequest;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface HandleShardedRequestOperation {
    <T extends ShardRequest, R, C> Uni<R> execute(Logger log,
                                                  T request,
                                                  Function<URI, C> api,
                                                  BiFunction<C, T, Uni<R>> route,
                                                  BiFunction<ShardModel, T, Uni<R>> handle);
}
