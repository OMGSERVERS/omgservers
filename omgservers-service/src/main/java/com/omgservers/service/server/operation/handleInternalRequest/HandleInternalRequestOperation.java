package com.omgservers.service.server.operation.handleInternalRequest;

import com.omgservers.schema.module.ShardedRequest;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface HandleInternalRequestOperation {
    <T extends ShardedRequest, R, C> Uni<R> handleInternalRequest(Logger log,
                                                                  T request,
                                                                  Function<URI, C> api,
                                                                  BiFunction<C, T, Uni<? extends R>> route,
                                                                  Function<T, Uni<? extends R>> handle);
}
