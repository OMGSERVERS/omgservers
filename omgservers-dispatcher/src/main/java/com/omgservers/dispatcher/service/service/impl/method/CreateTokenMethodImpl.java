package com.omgservers.dispatcher.service.service.impl.method;

import com.omgservers.dispatcher.operation.getDispatcherConfig.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.operation.getServiceDispatcherEntrypointClient.GetServiceDispatcherEntrypointClientOperation;
import com.omgservers.dispatcher.service.service.dto.CreateTokenRequest;
import com.omgservers.dispatcher.service.service.dto.CreateTokenResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final GetServiceDispatcherEntrypointClientOperation getServiceDispatcherEntrypointClientOperation;
    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    @Override
    public Uni<CreateTokenResponse> execute(final CreateTokenRequest request) {
        final var serviceUri = getDispatcherConfigOperation.getDispatcherConfig().serviceUri();
        final var serviceClient = getServiceDispatcherEntrypointClientOperation
                .getClient(serviceUri);

        final var user = request.getUser();
        final var password = request.getPassword();

        final var createTokenDispatcherRequest = new CreateTokenDispatcherRequest(user, password);
        return serviceClient.execute(createTokenDispatcherRequest)
                .map(CreateTokenDispatcherResponse::getRawToken)
                .map(CreateTokenResponse::new);
    }
}
