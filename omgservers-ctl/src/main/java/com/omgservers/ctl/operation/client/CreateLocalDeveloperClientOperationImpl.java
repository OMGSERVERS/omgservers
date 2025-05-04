package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.DeveloperClient;
import com.omgservers.ctl.configuration.LocalConfiguration;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLocalDeveloperClientOperationImpl implements CreateLocalDeveloperClientOperation {

    final CreateDeveloperAnonymousClientOperation createDeveloperAnonymousClientOperation;
    final CreateDeveloperClientOperation createDeveloperClientOperation;

    @Override
    public DeveloperClient execute(final String developer,
                                   final String password) {
        final var serviceUri = LocalConfiguration.API_URI;

        final var developerToken = developerCreateToken(serviceUri, developer, password);
        final var developerClient = createDeveloperClientOperation.execute(serviceUri, developerToken);

        return developerClient;
    }

    String developerCreateToken(final URI serviceUri,
                                final String developerUser,
                                final String developerPassword) {
        final var developerAnonymousClient = createDeveloperAnonymousClientOperation.execute(serviceUri);
        final var request = new CreateTokenDeveloperRequest(Long.valueOf(developerUser), developerPassword);
        return developerAnonymousClient.execute(request)
                .map(CreateTokenDeveloperResponse::getRawToken)
                .await().indefinitely();
    }
}
