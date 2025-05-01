package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.SupportClient;
import com.omgservers.ctl.configuration.CtlConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
class CreateSupportAnonymousClientOperationImpl implements CreateSupportAnonymousClientOperation {

    @Override
    public SupportClient execute(final URI uri) {
        final var client = RestClientBuilder.newBuilder()
                .baseUri(uri)
                .connectTimeout(CtlConfiguration.DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(CtlConfiguration.DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build(SupportClient.class);

        return client;
    }
}
