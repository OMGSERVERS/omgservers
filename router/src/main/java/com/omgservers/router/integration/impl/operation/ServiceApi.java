package com.omgservers.router.integration.impl.operation;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

public interface ServiceApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenRouterResponse> createToken(@NotNull CreateTokenRouterRequest request);

    @PUT
    @Path("/get-runtime-server-uri")
    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@NotNull GetRuntimeServerUriRouterRequest request);
}
