package com.omgservers.service.operation.server;

import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jboss.logmanager.MDC;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Function;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleApiRequestOperationImpl implements HandleApiRequestOperation {

    final SecurityIdentity securityIdentity;

    @Override
    public <T, R> Uni<R> handleApiRequest(Logger log, T request, Function<T, Uni<? extends R>> service) {
        if (securityIdentity.isAnonymous()) {
            MDC.put("subject", "anonymous");
        } else {
            final var subject = securityIdentity
                    .<String>getAttribute(SecurityAttributesEnum.SUBJECT.getAttributeName());
            if (Objects.nonNull(subject)) {
                MDC.put("subject", subject);
            } else {
                MDC.put("subject", "unknown");
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
