package com.omgservers.router.integration.impl.service.impl.method.getRuntimeServerUri;

import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import com.omgservers.router.integration.impl.operation.GetServiceClientOperation;
import com.omgservers.router.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GetRuntimeServerUriMethodImpl implements GetRuntimeServerUriMethod {

    final GetServiceClientOperation getServiceClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(final GetRuntimeServerUriRouterRequest request) {
        final var serviceUri = getConfigOperation.getRouterConfig().serviceUri();
        final var serviceClient = getServiceClientOperation.getClient(serviceUri);
        return serviceClient.getRuntimeServerUri(request);
    }
}
