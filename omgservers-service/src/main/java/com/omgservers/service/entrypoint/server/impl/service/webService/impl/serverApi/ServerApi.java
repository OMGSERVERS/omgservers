package com.omgservers.service.entrypoint.server.impl.service.webService.impl.serverApi;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Server Entrypoint API")
@Path("/omgservers/v1/entrypoint/server/request")
public interface ServerApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerAdminResponse> pingServer();

    @PUT
    @Path("/generate-id")
    Uni<GenerateIdAdminResponse> generateId();

    @PUT
    @Path("/bcrypt-hash")
    Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request);
}
