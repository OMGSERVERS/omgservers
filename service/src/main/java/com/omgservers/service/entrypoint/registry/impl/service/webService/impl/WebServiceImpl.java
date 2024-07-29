package com.omgservers.service.entrypoint.registry.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;
import com.omgservers.service.entrypoint.registry.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final RegistryService registryService;

    @Override
    public Uni<Void> handleEvents(final HandleEventsRegistryRequest request) {
        return registryService.handleEvents(request);
    }

    @Override
    public Uni<BasicAuthRegistryResponse> basicAuth(final BasicAuthRegistryRequest request) {
        return registryService.basicAuth(request);
    }

    @Override
    public Uni<OAuth2RegistryResponse> oAuth2(final OAuth2RegistryRequest request) {
        return registryService.oAuth2(request);
    }
}
