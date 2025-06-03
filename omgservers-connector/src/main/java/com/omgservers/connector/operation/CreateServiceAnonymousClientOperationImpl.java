package com.omgservers.connector.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@Slf4j
@ApplicationScoped
class CreateServiceAnonymousClientOperationImpl implements CreateServiceAnonymousClientOperation {

    @Override
    public ServiceAnonymousClient execute(final URI uri) {
        final var client = RestClientBuilder.newBuilder()
                .baseUri(uri)
                .build(ServiceAnonymousClient.class);

        return client;
    }
}
