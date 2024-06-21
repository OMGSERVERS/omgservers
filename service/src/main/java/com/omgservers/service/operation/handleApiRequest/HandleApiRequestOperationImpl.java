package com.omgservers.service.operation.handleApiRequest;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleApiRequestOperationImpl implements HandleApiRequestOperation {

    final SecurityIdentity securityIdentity;

    @Override
    public <T, R> Uni<R> handleApiRequest(Logger log, T request, Function<T, Uni<? extends R>> service) {
        return Uni.createFrom().item(request)
                .flatMap(service)
                .onFailure()
                .invoke(t -> log.warn("Request failed, request={}, {}:{}",
                        request,
                        t.getClass().getSimpleName(),
                        t.getMessage()));
    }
}
