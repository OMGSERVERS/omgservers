package com.omgservers.api;

import com.omgservers.api.configuration.OpenApiConfiguration;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime Entrypoint API")
@Path("/service/v1/entrypoint/runtime/request")
@SecurityRequirement(name = OpenApiConfiguration.RUNTIME_SECURITY_SCHEMA)
public interface RuntimeApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenRuntimeResponse> execute(@NotNull CreateTokenRuntimeRequest request);

    @POST
    @Path("/interchange-messages")
    Uni<InterchangeMessagesRuntimeResponse> execute(@NotNull InterchangeMessagesRuntimeRequest request);
}
