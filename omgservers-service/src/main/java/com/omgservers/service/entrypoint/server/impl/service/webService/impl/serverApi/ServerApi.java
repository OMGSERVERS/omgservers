package com.omgservers.service.entrypoint.server.impl.service.webService.impl.serverApi;

import com.omgservers.model.dto.server.BcryptHashServerRequest;
import com.omgservers.model.dto.server.BcryptHashServerResponse;
import com.omgservers.model.dto.server.GenerateIdServerResponse;
import com.omgservers.model.dto.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Server Entrypoint API")
@Path("/omgservers/v1/entrypoint/server/request")
public interface ServerApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerServerResponse> pingServer();

    @PUT
    @Path("/generate-id")
    Uni<GenerateIdServerResponse> generateId();

    @PUT
    @Path("/bcrypt-hash")
    Uni<BcryptHashServerResponse> bcryptHash(BcryptHashServerRequest request);
}
