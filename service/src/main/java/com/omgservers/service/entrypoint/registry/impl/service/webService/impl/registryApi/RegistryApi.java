package com.omgservers.service.entrypoint.registry.impl.service.webService.impl.registryApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Registry Entrypoint API")
@Path("/omgservers/v1/entrypoint/registry/event")
@SecurityRequirement(name = ServiceOpenApiConfiguration.REGISTRY_SECURITY_SCHEMA)
public interface RegistryApi {

    @POST
    Uni<Void> handleEvent(@NotNull JsonNode jsonNode);
}
