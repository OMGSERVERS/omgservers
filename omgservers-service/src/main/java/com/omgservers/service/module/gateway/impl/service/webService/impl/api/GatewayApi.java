package com.omgservers.service.module.gateway.impl.service.webService.impl.api;

import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.gateway.CloseConnectionRequest;
import com.omgservers.model.dto.gateway.CloseConnectionResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/gateway-api/v1/request")
public interface GatewayApi {

    @PUT
    @Path("/close-connection")
    Uni<CloseConnectionResponse> closeConnection(CloseConnectionRequest request);

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
