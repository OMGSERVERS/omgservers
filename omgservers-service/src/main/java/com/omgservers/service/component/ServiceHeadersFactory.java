package com.omgservers.service.component;

import com.omgservers.service.operation.issueJwtToken.IssueJwtTokenOperation;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class ServiceHeadersFactory extends ReactiveClientHeadersFactory {

    final String serviceJwtToken;

    public ServiceHeadersFactory(final IssueJwtTokenOperation issueJwtTokenOperation) {
        serviceJwtToken = issueJwtTokenOperation.issueServiceJwtToken();
        log.info("Service JWT token was issued, {}", serviceJwtToken);
    }

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(final MultivaluedMap<String, String> incomingHeaders,
                                                          final MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        multivaluedHashMap.add("Authorization", "Bearer " + serviceJwtToken);
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
