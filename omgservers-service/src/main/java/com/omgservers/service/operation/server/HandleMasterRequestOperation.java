package com.omgservers.service.operation.server;

import com.omgservers.schema.master.MasterRequest;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface HandleMasterRequestOperation {
    <T extends MasterRequest, R, C> Uni<R> execute(Logger log,
                                                   T request,
                                                   Function<URI, C> api,
                                                   BiFunction<C, T, Uni<R>> route,
                                                   Function<T, Uni<R>> handle);
}
