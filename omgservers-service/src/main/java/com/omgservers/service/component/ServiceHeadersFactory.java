package com.omgservers.service.component;

import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

    final GetConfigOperation getConfigOperation;

    final ServiceTokenFactory serviceTokenFactory;

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(final MultivaluedMap<String, String> incomingHeaders,
                                                          final MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        multivaluedHashMap.add("User-Agent", getConfigOperation.getServiceConfig().index().serverUri().getHost());
        multivaluedHashMap.add("Authorization", "Bearer " + serviceTokenFactory.getServiceJwtToken());
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
