package com.omgservers.router.integration.impl.operation;

import com.omgservers.router.components.TokenContainer;
import com.omgservers.router.operation.getConfig.GetConfigOperation;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServiceClientHeadersFactory extends ReactiveClientHeadersFactory {

    final GetConfigOperation getConfigOperation;
    final TokenContainer tokenContainer;
    final String userAgent;

    public ServiceClientHeadersFactory(final GetConfigOperation getConfigOperation,
                                       final TokenContainer tokenContainer) {
        this.getConfigOperation = getConfigOperation;
        this.tokenContainer = tokenContainer;
        userAgent = "router";
    }

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(MultivaluedMap<String, String> incomingHeaders,
                                                          MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        multivaluedHashMap.add("User-Agent", userAgent);
        multivaluedHashMap.add("Authorization", "Bearer " + tokenContainer.get());
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
