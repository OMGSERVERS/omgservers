package com.omgservers.service.operation.server;

import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleApiRequestOperationImpl implements HandleApiRequestOperation {

    final PutIntoMdcOperation putIntoMdcOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public <T, R> Uni<R> handleApiRequest(Logger log, T request, Function<T, Uni<? extends R>> service) {
        if (securityIdentity.isAnonymous()) {
            putIntoMdcOperation.putAnonymousSubject();
        } else {
            final var subject = getSecurityAttributeOperation.<String>getSubject();
            if (Objects.nonNull(subject)) {
                putIntoMdcOperation.putArbitrarySubject(subject);
            } else {
                putIntoMdcOperation.putUnknownSubject();
            }
        }

        return Uni.createFrom().item(request)
                .flatMap(service)
                .onFailure()
                .invoke(t -> log.debug("Failed, {}, {}:{}",
                        request,
                        t.getClass().getSimpleName(),
                        t.getMessage()));
    }
}
