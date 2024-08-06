package com.omgservers.service.entrypoint.registry.impl.service.webService;

import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<Void> handleEvents(HandleEventsRegistryRequest request);

    Uni<BasicAuthRegistryResponse> basicAuth(BasicAuthRegistryRequest request);

    Uni<OAuth2RegistryResponse> oAuth2(OAuth2RegistryRequest request);
}
