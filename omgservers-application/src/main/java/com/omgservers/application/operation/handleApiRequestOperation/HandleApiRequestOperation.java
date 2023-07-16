package com.omgservers.application.operation.handleApiRequestOperation;

import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;

import java.util.function.Function;

public interface HandleApiRequestOperation {
    <T, R> Uni<R> handleApiRequest(Logger log, T request, Function<T, Uni<? extends R>> handler);
}
