package com.omgservers.module.gateway.impl.service.webService.impl.api;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.AssignRuntimeResponse;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.dto.gateway.RevokeRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/gateway-api/v1/request")
public interface GatewayApi {

    @PUT
    @Path("/respond-message")
    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);

    @PUT
    @Path("/assign-client")
    Uni<AssignClientResponse> assignClient(AssignClientRequest request);

    @PUT
    @Path("/assign-runtime")
    Uni<AssignRuntimeResponse> assignRuntime(AssignRuntimeRequest request);

    @PUT
    @Path("/revoke-runtime")
    Uni<RevokeRuntimeResponse> revokeRuntime(RevokeRuntimeRequest request);
}
