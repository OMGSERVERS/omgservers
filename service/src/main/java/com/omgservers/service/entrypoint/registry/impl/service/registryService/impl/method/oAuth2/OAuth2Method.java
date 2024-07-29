package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.oAuth2;

import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import io.smallrye.mutiny.Uni;

public interface OAuth2Method {
    Uni<OAuth2RegistryResponse> oAuth2(OAuth2RegistryRequest request);
}
