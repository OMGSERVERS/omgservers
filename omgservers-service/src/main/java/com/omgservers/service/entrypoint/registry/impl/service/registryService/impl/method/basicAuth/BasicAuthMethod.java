package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.basicAuth;

import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import io.smallrye.mutiny.Uni;

public interface BasicAuthMethod {
    Uni<BasicAuthRegistryResponse> basicAuth(BasicAuthRegistryRequest request);
}
