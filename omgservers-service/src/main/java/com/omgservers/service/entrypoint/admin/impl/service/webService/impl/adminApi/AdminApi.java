package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Admin Entrypoint API")
@Path("/omgservers/v1/entrypoint/admin/request")
public interface AdminApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerAdminResponse> pingServer();

    @PUT
    @Path("/generate-id")
    Uni<GenerateIdAdminResponse> generateId();

    @PUT
    @Path("/bcrypt-hash")
    Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request);

    @PUT
    @Path("/bootstrap-index")
    Uni<BootstrapIndexServerResponse> bootstrapIndex(BootstrapIndexServerRequest request);
}
