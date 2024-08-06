package com.omgservers.service.entrypoint.registry.impl.service.webService.impl.registryApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Registry Entrypoint API")
@Path("/omgservers/v1/entrypoint/registry")
@SecurityRequirement(name = ServiceOpenApiConfiguration.REGISTRY_SECURITY_SCHEMA)
public interface RegistryApi {

    @POST
    @Path("/events")
    Uni<Void> handleEvents(@NotNull JsonNode jsonNode);

    @GET
    @Path("/token")
    Uni<BasicAuthRegistryResponse> basicAuth(@NotNull @BeanParam BasicAuthRegistryRequest request);

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Uni<OAuth2RegistryResponse> oAuth2(@NotNull @BeanParam OAuth2RegistryRequest request);
}
