package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl;

import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.basicAuth.BasicAuthMethod;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.handleEvents.HandleEventsMethod;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.oAuth2.OAuth2Method;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RegistryServiceImpl implements RegistryService {

    final HandleEventsMethod handleEventsMethod;
    final BasicAuthMethod basicAuthMethod;
    final OAuth2Method oAuth2Method;

    @Override
    public Uni<Void> handleEvents(@Valid final HandleEventsRegistryRequest request) {
        return handleEventsMethod.handleEvents(request);
    }

    @Override
    public Uni<BasicAuthRegistryResponse> basicAuth(@Valid final BasicAuthRegistryRequest request) {
        return basicAuthMethod.basicAuth(request);
    }

    @Override
    public Uni<OAuth2RegistryResponse> oAuth2(@Valid final OAuth2RegistryRequest request) {
        return oAuth2Method.oAuth2(request);
    }
}
