package com.omgservers.operation.handleInternalRequest;

import com.omgservers.dto.ShardRequest;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface HandleInternalRequestOperation {
    <T extends ShardRequest, R, C> Uni<R> handleInternalRequest(Logger log,
                                                                T request,
                                                                Consumer<T> validation,
                                                                Function<URI, C> api,
                                                                BiFunction<C, T, Uni<? extends R>> route,
                                                                Function<T, Uni<? extends R>> handle);
}
