package com.omgservers.service.entrypoint.server.impl.service.webService.impl.serverApi;

import com.omgservers.schema.entrypoint.server.BcryptHashServerRequest;
import com.omgservers.schema.entrypoint.server.BcryptHashServerResponse;
import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
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
    Uni<BcryptHashServerResponse> execute(@NotNull BcryptHashServerRequest request);
}
