package com.omgservers.base.operation.handleApiRequest;

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
        log.info("Request, principal={}, {}", securityIdentity.getPrincipal().getName(), request);
        return Uni.createFrom().item(request)
                .flatMap(service)
                .invoke(response -> {
                    if (response != null) {
                        log.info("Request was finished, {}", response);
                    } else {
                        log.info("Request was finished");
                    }
                })
                .onFailure().invoke(t -> log.error("Request failed, {}", t.getMessage()));
    }
}
