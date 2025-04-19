package com.omgservers.service.operation.server;

import com.omgservers.schema.master.MasterRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor
class HandleMasterRequestOperationImpl implements HandleMasterRequestOperation {

    final GetServiceConfigOperation getServiceConfigOperation;
    final PutIntoMdcOperation putIntoMdcOperation;

    @Override
    public <T extends MasterRequest, R, C> Uni<R> execute(final Logger log,
                                                          final T request,
                                                          final Function<URI, C> api,
                                                          final BiFunction<C, T, Uni<R>> route,
                                                          final Function<T, Uni<R>> handle) {
        final var masterUri = getServiceConfigOperation.getServiceConfig().master().uri();
        final var thisUri = getServiceConfigOperation.getServiceConfig().shard().uri();

        final Uni<R> operation;
        if (masterUri.equals(thisUri)) {
            log.trace("Handle request, request={}", request);
            operation = handle.apply(request);
        } else {
            final var client = api.apply(masterUri);
            log.trace("Route request, targetServer={}, request={}", masterUri, request);
            operation = route.apply(client, request);
        }

        return operation
                .onFailure()
                .invoke(t -> log.error("Master request failed, uri={}, request={}, {}:{}",
                        masterUri, request, t.getClass().getSimpleName(), t.getMessage()));
    }
}
