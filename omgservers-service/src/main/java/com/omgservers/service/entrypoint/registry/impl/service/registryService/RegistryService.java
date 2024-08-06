package com.omgservers.service.entrypoint.registry.impl.service.registryService;

import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<Void> handleEvents(@Valid HandleEventsRegistryRequest request);

    Uni<BasicAuthRegistryResponse> basicAuth(@Valid BasicAuthRegistryRequest request);

    Uni<OAuth2RegistryResponse> oAuth2(@Valid OAuth2RegistryRequest request);
}
