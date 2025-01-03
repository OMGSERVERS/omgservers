package com.omgservers.dispatcher.component;

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
public class AnonymousHeadersFactory extends ReactiveClientHeadersFactory {

    final DispatcherTokenContainer dispatcherTokenContainer;

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(final MultivaluedMap<String, String> incomingHeaders,
                                                          final MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        multivaluedHashMap.add("User-Agent", "dispatcher");
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
