package com.omgservers.service.component;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
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
public class ServiceHeadersFactory extends ReactiveClientHeadersFactory {

    final GetServiceConfigOperation getServiceConfigOperation;

    final ServiceTokenFactory serviceTokenFactory;

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(final MultivaluedMap<String, String> incomingHeaders,
                                                          final MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        multivaluedHashMap.add("User-Agent", getServiceConfigOperation
                .getServiceConfig().server().uri().getHost());
        multivaluedHashMap.add("Authorization", "Bearer " + serviceTokenFactory.getServiceJwtToken());
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
