package com.omgservers.service.entrypoint.runtime.impl.service.webService.impl.api;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime Entrypoint API")
@Path("/omgservers/v1/entrypoint/runtime/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.RUNTIME_SECURITY_SCHEMA)
public interface RuntimeApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenRuntimeResponse> createToken(@NotNull CreateTokenRuntimeRequest request);

    @PUT
    @Path("/get-config")
    Uni<GetConfigRuntimeResponse> getConfig(@NotNull GetConfigRuntimeRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangeRuntimeResponse> interchange(@NotNull InterchangeRuntimeRequest request);
}
