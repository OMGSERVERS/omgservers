package com.omgservers.service.entrypoint.runtime.impl.service.webService.impl.api;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime Entrypoint API")
@Path("/service/v1/entrypoint/runtime/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.RUNTIME_SECURITY_SCHEMA)
public interface RuntimeApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenRuntimeResponse> execute(@NotNull CreateTokenRuntimeRequest request);

    @POST
    @Path("/interchange")
    Uni<InterchangeRuntimeResponse> execute(@NotNull InterchangeRuntimeRequest request);
}
