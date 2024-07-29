package com.omgservers.router.integration.impl.service.impl.method.createToken;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import com.omgservers.router.integration.impl.operation.GetServiceClientOperation;
import com.omgservers.router.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final GetServiceClientOperation getServiceClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<CreateTokenRouterResponse> createToken(final CreateTokenRouterRequest request) {
        final var serviceUri = getConfigOperation.getRouterConfig().serviceUri();
        final var serviceClient = getServiceClientOperation.getClient(serviceUri);
        return serviceClient.createToken(request);
    }
}
