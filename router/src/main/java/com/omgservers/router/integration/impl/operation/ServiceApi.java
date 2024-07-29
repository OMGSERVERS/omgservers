package com.omgservers.router.integration.impl.operation;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/v1/entrypoint/router/request")
public interface ServiceApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenRouterResponse> createToken(@NotNull CreateTokenRouterRequest request);

    @PUT
    @Path("/get-runtime-server-uri")
    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@NotNull GetRuntimeServerUriRouterRequest request);
}
