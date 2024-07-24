package com.omgservers.service.entrypoint.router.impl.service.webService.impl.routerApi;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Router Entrypoint API")
@Path("/omgservers/v1/entrypoint/router/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.ROUTER_SECURITY_SCHEMA)
public interface RouterApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenRouterResponse> createToken(@NotNull CreateTokenRouterRequest request);

    @PUT
    @Path("/get-runtime-server-uri")
    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@NotNull GetRuntimeServerUriRouterRequest request);
}
